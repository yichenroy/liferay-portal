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

package com.liferay.commerce.forecast.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.forecast.model.CommerceForecastEntry;
import com.liferay.commerce.forecast.model.CommerceForecastEntryModel;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

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
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the CommerceForecastEntry service. Represents a row in the &quot;CommerceForecastEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CommerceForecastEntryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceForecastEntryImpl}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastEntryImpl
 * @see CommerceForecastEntry
 * @see CommerceForecastEntryModel
 * @generated
 */
@ProviderType
public class CommerceForecastEntryModelImpl extends BaseModelImpl<CommerceForecastEntry>
	implements CommerceForecastEntryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce forecast entry model instance should use the {@link CommerceForecastEntry} interface instead.
	 */
	public static final String TABLE_NAME = "CommerceForecastEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "commerceForecastEntryId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "time_", Types.BIGINT },
			{ "period", Types.INTEGER },
			{ "target", Types.INTEGER },
			{ "customerId", Types.BIGINT },
			{ "CPInstanceId", Types.BIGINT },
			{ "assertivity", Types.DECIMAL }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("commerceForecastEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("time_", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("period", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("target", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("customerId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("CPInstanceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("assertivity", Types.DECIMAL);
	}

	public static final String TABLE_SQL_CREATE = "create table CommerceForecastEntry (commerceForecastEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,time_ LONG,period INTEGER,target INTEGER,customerId LONG,CPInstanceId LONG,assertivity DECIMAL(30, 16) null)";
	public static final String TABLE_SQL_DROP = "drop table CommerceForecastEntry";
	public static final String ORDER_BY_JPQL = " ORDER BY commerceForecastEntry.time ASC";
	public static final String ORDER_BY_SQL = " ORDER BY CommerceForecastEntry.time_ ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.forecast.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.forecast.model.CommerceForecastEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.forecast.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.forecast.model.CommerceForecastEntry"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.forecast.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.forecast.model.CommerceForecastEntry"),
			true);
	public static final long CPINSTANCEID_COLUMN_BITMASK = 1L;
	public static final long COMPANYID_COLUMN_BITMASK = 2L;
	public static final long CUSTOMERID_COLUMN_BITMASK = 4L;
	public static final long PERIOD_COLUMN_BITMASK = 8L;
	public static final long TARGET_COLUMN_BITMASK = 16L;
	public static final long TIME_COLUMN_BITMASK = 32L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.forecast.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.forecast.model.CommerceForecastEntry"));

	public CommerceForecastEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceForecastEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceForecastEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceForecastEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceForecastEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceForecastEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceForecastEntryId", getCommerceForecastEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("time", getTime());
		attributes.put("period", getPeriod());
		attributes.put("target", getTarget());
		attributes.put("customerId", getCustomerId());
		attributes.put("CPInstanceId", getCPInstanceId());
		attributes.put("assertivity", getAssertivity());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceForecastEntryId = (Long)attributes.get(
				"commerceForecastEntryId");

		if (commerceForecastEntryId != null) {
			setCommerceForecastEntryId(commerceForecastEntryId);
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

		Long time = (Long)attributes.get("time");

		if (time != null) {
			setTime(time);
		}

		Integer period = (Integer)attributes.get("period");

		if (period != null) {
			setPeriod(period);
		}

		Integer target = (Integer)attributes.get("target");

		if (target != null) {
			setTarget(target);
		}

		Long customerId = (Long)attributes.get("customerId");

		if (customerId != null) {
			setCustomerId(customerId);
		}

		Long CPInstanceId = (Long)attributes.get("CPInstanceId");

		if (CPInstanceId != null) {
			setCPInstanceId(CPInstanceId);
		}

		BigDecimal assertivity = (BigDecimal)attributes.get("assertivity");

		if (assertivity != null) {
			setAssertivity(assertivity);
		}
	}

	@Override
	public long getCommerceForecastEntryId() {
		return _commerceForecastEntryId;
	}

	@Override
	public void setCommerceForecastEntryId(long commerceForecastEntryId) {
		_commerceForecastEntryId = commerceForecastEntryId;
	}

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
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
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

		_modifiedDate = modifiedDate;
	}

	@Override
	public long getTime() {
		return _time;
	}

	@Override
	public void setTime(long time) {
		_columnBitmask = -1L;

		_time = time;
	}

	@Override
	public int getPeriod() {
		return _period;
	}

	@Override
	public void setPeriod(int period) {
		_columnBitmask |= PERIOD_COLUMN_BITMASK;

		if (!_setOriginalPeriod) {
			_setOriginalPeriod = true;

			_originalPeriod = _period;
		}

		_period = period;
	}

	public int getOriginalPeriod() {
		return _originalPeriod;
	}

	@Override
	public int getTarget() {
		return _target;
	}

	@Override
	public void setTarget(int target) {
		_columnBitmask |= TARGET_COLUMN_BITMASK;

		if (!_setOriginalTarget) {
			_setOriginalTarget = true;

			_originalTarget = _target;
		}

		_target = target;
	}

	public int getOriginalTarget() {
		return _originalTarget;
	}

	@Override
	public long getCustomerId() {
		return _customerId;
	}

	@Override
	public void setCustomerId(long customerId) {
		_columnBitmask |= CUSTOMERID_COLUMN_BITMASK;

		if (!_setOriginalCustomerId) {
			_setOriginalCustomerId = true;

			_originalCustomerId = _customerId;
		}

		_customerId = customerId;
	}

	public long getOriginalCustomerId() {
		return _originalCustomerId;
	}

	@Override
	public long getCPInstanceId() {
		return _CPInstanceId;
	}

	@Override
	public void setCPInstanceId(long CPInstanceId) {
		_columnBitmask |= CPINSTANCEID_COLUMN_BITMASK;

		if (!_setOriginalCPInstanceId) {
			_setOriginalCPInstanceId = true;

			_originalCPInstanceId = _CPInstanceId;
		}

		_CPInstanceId = CPInstanceId;
	}

	public long getOriginalCPInstanceId() {
		return _originalCPInstanceId;
	}

	@Override
	public BigDecimal getAssertivity() {
		return _assertivity;
	}

	@Override
	public void setAssertivity(BigDecimal assertivity) {
		_assertivity = assertivity;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CommerceForecastEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceForecastEntry toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CommerceForecastEntry)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CommerceForecastEntryImpl commerceForecastEntryImpl = new CommerceForecastEntryImpl();

		commerceForecastEntryImpl.setCommerceForecastEntryId(getCommerceForecastEntryId());
		commerceForecastEntryImpl.setCompanyId(getCompanyId());
		commerceForecastEntryImpl.setUserId(getUserId());
		commerceForecastEntryImpl.setUserName(getUserName());
		commerceForecastEntryImpl.setCreateDate(getCreateDate());
		commerceForecastEntryImpl.setModifiedDate(getModifiedDate());
		commerceForecastEntryImpl.setTime(getTime());
		commerceForecastEntryImpl.setPeriod(getPeriod());
		commerceForecastEntryImpl.setTarget(getTarget());
		commerceForecastEntryImpl.setCustomerId(getCustomerId());
		commerceForecastEntryImpl.setCPInstanceId(getCPInstanceId());
		commerceForecastEntryImpl.setAssertivity(getAssertivity());

		commerceForecastEntryImpl.resetOriginalValues();

		return commerceForecastEntryImpl;
	}

	@Override
	public int compareTo(CommerceForecastEntry commerceForecastEntry) {
		int value = 0;

		if (getTime() < commerceForecastEntry.getTime()) {
			value = -1;
		}
		else if (getTime() > commerceForecastEntry.getTime()) {
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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceForecastEntry)) {
			return false;
		}

		CommerceForecastEntry commerceForecastEntry = (CommerceForecastEntry)obj;

		long primaryKey = commerceForecastEntry.getPrimaryKey();

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
		CommerceForecastEntryModelImpl commerceForecastEntryModelImpl = this;

		commerceForecastEntryModelImpl._originalCompanyId = commerceForecastEntryModelImpl._companyId;

		commerceForecastEntryModelImpl._setOriginalCompanyId = false;

		commerceForecastEntryModelImpl._setModifiedDate = false;

		commerceForecastEntryModelImpl._originalPeriod = commerceForecastEntryModelImpl._period;

		commerceForecastEntryModelImpl._setOriginalPeriod = false;

		commerceForecastEntryModelImpl._originalTarget = commerceForecastEntryModelImpl._target;

		commerceForecastEntryModelImpl._setOriginalTarget = false;

		commerceForecastEntryModelImpl._originalCustomerId = commerceForecastEntryModelImpl._customerId;

		commerceForecastEntryModelImpl._setOriginalCustomerId = false;

		commerceForecastEntryModelImpl._originalCPInstanceId = commerceForecastEntryModelImpl._CPInstanceId;

		commerceForecastEntryModelImpl._setOriginalCPInstanceId = false;

		commerceForecastEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CommerceForecastEntry> toCacheModel() {
		CommerceForecastEntryCacheModel commerceForecastEntryCacheModel = new CommerceForecastEntryCacheModel();

		commerceForecastEntryCacheModel.commerceForecastEntryId = getCommerceForecastEntryId();

		commerceForecastEntryCacheModel.companyId = getCompanyId();

		commerceForecastEntryCacheModel.userId = getUserId();

		commerceForecastEntryCacheModel.userName = getUserName();

		String userName = commerceForecastEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceForecastEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceForecastEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			commerceForecastEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceForecastEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			commerceForecastEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceForecastEntryCacheModel.time = getTime();

		commerceForecastEntryCacheModel.period = getPeriod();

		commerceForecastEntryCacheModel.target = getTarget();

		commerceForecastEntryCacheModel.customerId = getCustomerId();

		commerceForecastEntryCacheModel.CPInstanceId = getCPInstanceId();

		commerceForecastEntryCacheModel.assertivity = getAssertivity();

		return commerceForecastEntryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{commerceForecastEntryId=");
		sb.append(getCommerceForecastEntryId());
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
		sb.append(", time=");
		sb.append(getTime());
		sb.append(", period=");
		sb.append(getPeriod());
		sb.append(", target=");
		sb.append(getTarget());
		sb.append(", customerId=");
		sb.append(getCustomerId());
		sb.append(", CPInstanceId=");
		sb.append(getCPInstanceId());
		sb.append(", assertivity=");
		sb.append(getAssertivity());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append("com.liferay.commerce.forecast.model.CommerceForecastEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>commerceForecastEntryId</column-name><column-value><![CDATA[");
		sb.append(getCommerceForecastEntryId());
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
			"<column><column-name>time</column-name><column-value><![CDATA[");
		sb.append(getTime());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>period</column-name><column-value><![CDATA[");
		sb.append(getPeriod());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>target</column-name><column-value><![CDATA[");
		sb.append(getTarget());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>customerId</column-name><column-value><![CDATA[");
		sb.append(getCustomerId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CPInstanceId</column-name><column-value><![CDATA[");
		sb.append(getCPInstanceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>assertivity</column-name><column-value><![CDATA[");
		sb.append(getAssertivity());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CommerceForecastEntry.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CommerceForecastEntry.class, ModelWrapper.class
		};
	private long _commerceForecastEntryId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _time;
	private int _period;
	private int _originalPeriod;
	private boolean _setOriginalPeriod;
	private int _target;
	private int _originalTarget;
	private boolean _setOriginalTarget;
	private long _customerId;
	private long _originalCustomerId;
	private boolean _setOriginalCustomerId;
	private long _CPInstanceId;
	private long _originalCPInstanceId;
	private boolean _setOriginalCPInstanceId;
	private BigDecimal _assertivity;
	private long _columnBitmask;
	private CommerceForecastEntry _escapedModel;
}