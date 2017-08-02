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

package com.liferay.commerce.address.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.address.model.CommerceRegion;
import com.liferay.commerce.address.model.CommerceRegionModel;
import com.liferay.commerce.address.model.CommerceRegionSoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the CommerceRegion service. Represents a row in the &quot;CommerceRegion&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CommerceRegionModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceRegionImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionImpl
 * @see CommerceRegion
 * @see CommerceRegionModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class CommerceRegionModelImpl extends BaseModelImpl<CommerceRegion>
	implements CommerceRegionModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce region model instance should use the {@link CommerceRegion} interface instead.
	 */
	public static final String TABLE_NAME = "CommerceRegion";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "commerceRegionId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "commerceCountryId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "code_", Types.VARCHAR },
			{ "priority", Types.DOUBLE },
			{ "active_", Types.BOOLEAN },
			{ "lastPublishDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("commerceRegionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("commerceCountryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("code_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);
		TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table CommerceRegion (uuid_ VARCHAR(75) null,commerceRegionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceCountryId LONG,name VARCHAR(75) null,code_ VARCHAR(75) null,priority DOUBLE,active_ BOOLEAN,lastPublishDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table CommerceRegion";
	public static final String ORDER_BY_JPQL = " ORDER BY commerceRegion.priority ASC";
	public static final String ORDER_BY_SQL = " ORDER BY CommerceRegion.priority ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.address.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.address.model.CommerceRegion"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.address.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.address.model.CommerceRegion"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.address.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.address.model.CommerceRegion"),
			true);
	public static final long ACTIVE_COLUMN_BITMASK = 1L;
	public static final long COMMERCECOUNTRYID_COLUMN_BITMASK = 2L;
	public static final long COMPANYID_COLUMN_BITMASK = 4L;
	public static final long GROUPID_COLUMN_BITMASK = 8L;
	public static final long UUID_COLUMN_BITMASK = 16L;
	public static final long PRIORITY_COLUMN_BITMASK = 32L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CommerceRegion toModel(CommerceRegionSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CommerceRegion model = new CommerceRegionImpl();

		model.setUuid(soapModel.getUuid());
		model.setCommerceRegionId(soapModel.getCommerceRegionId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCommerceCountryId(soapModel.getCommerceCountryId());
		model.setName(soapModel.getName());
		model.setCode(soapModel.getCode());
		model.setPriority(soapModel.getPriority());
		model.setActive(soapModel.getActive());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CommerceRegion> toModels(CommerceRegionSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CommerceRegion> models = new ArrayList<CommerceRegion>(soapModels.length);

		for (CommerceRegionSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.address.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.address.model.CommerceRegion"));

	public CommerceRegionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceRegionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceRegionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceRegionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceRegion.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceRegion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceRegionId", getCommerceRegionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceCountryId", getCommerceCountryId());
		attributes.put("name", getName());
		attributes.put("code", getCode());
		attributes.put("priority", getPriority());
		attributes.put("active", getActive());
		attributes.put("lastPublishDate", getLastPublishDate());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceRegionId = (Long)attributes.get("commerceRegionId");

		if (commerceRegionId != null) {
			setCommerceRegionId(commerceRegionId);
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

		Long commerceCountryId = (Long)attributes.get("commerceCountryId");

		if (commerceCountryId != null) {
			setCommerceCountryId(commerceCountryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String code = (String)attributes.get("code");

		if (code != null) {
			setCode(code);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getCommerceRegionId() {
		return _commerceRegionId;
	}

	@Override
	public void setCommerceRegionId(long commerceRegionId) {
		_commerceRegionId = commerceRegionId;
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
	public long getCommerceCountryId() {
		return _commerceCountryId;
	}

	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		_columnBitmask |= COMMERCECOUNTRYID_COLUMN_BITMASK;

		if (!_setOriginalCommerceCountryId) {
			_setOriginalCommerceCountryId = true;

			_originalCommerceCountryId = _commerceCountryId;
		}

		_commerceCountryId = commerceCountryId;
	}

	public long getOriginalCommerceCountryId() {
		return _originalCommerceCountryId;
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
	public void setName(String name) {
		_name = name;
	}

	@JSON
	@Override
	public String getCode() {
		if (_code == null) {
			return StringPool.BLANK;
		}
		else {
			return _code;
		}
	}

	@Override
	public void setCode(String code) {
		_code = code;
	}

	@JSON
	@Override
	public double getPriority() {
		return _priority;
	}

	@Override
	public void setPriority(double priority) {
		_columnBitmask = -1L;

		_priority = priority;
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
		_columnBitmask |= ACTIVE_COLUMN_BITMASK;

		if (!_setOriginalActive) {
			_setOriginalActive = true;

			_originalActive = _active;
		}

		_active = active;
	}

	public boolean getOriginalActive() {
		return _originalActive;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				CommerceRegion.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CommerceRegion.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceRegion toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CommerceRegion)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CommerceRegionImpl commerceRegionImpl = new CommerceRegionImpl();

		commerceRegionImpl.setUuid(getUuid());
		commerceRegionImpl.setCommerceRegionId(getCommerceRegionId());
		commerceRegionImpl.setGroupId(getGroupId());
		commerceRegionImpl.setCompanyId(getCompanyId());
		commerceRegionImpl.setUserId(getUserId());
		commerceRegionImpl.setUserName(getUserName());
		commerceRegionImpl.setCreateDate(getCreateDate());
		commerceRegionImpl.setModifiedDate(getModifiedDate());
		commerceRegionImpl.setCommerceCountryId(getCommerceCountryId());
		commerceRegionImpl.setName(getName());
		commerceRegionImpl.setCode(getCode());
		commerceRegionImpl.setPriority(getPriority());
		commerceRegionImpl.setActive(getActive());
		commerceRegionImpl.setLastPublishDate(getLastPublishDate());

		commerceRegionImpl.resetOriginalValues();

		return commerceRegionImpl;
	}

	@Override
	public int compareTo(CommerceRegion commerceRegion) {
		int value = 0;

		if (getPriority() < commerceRegion.getPriority()) {
			value = -1;
		}
		else if (getPriority() > commerceRegion.getPriority()) {
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

		if (!(obj instanceof CommerceRegion)) {
			return false;
		}

		CommerceRegion commerceRegion = (CommerceRegion)obj;

		long primaryKey = commerceRegion.getPrimaryKey();

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
		CommerceRegionModelImpl commerceRegionModelImpl = this;

		commerceRegionModelImpl._originalUuid = commerceRegionModelImpl._uuid;

		commerceRegionModelImpl._originalGroupId = commerceRegionModelImpl._groupId;

		commerceRegionModelImpl._setOriginalGroupId = false;

		commerceRegionModelImpl._originalCompanyId = commerceRegionModelImpl._companyId;

		commerceRegionModelImpl._setOriginalCompanyId = false;

		commerceRegionModelImpl._setModifiedDate = false;

		commerceRegionModelImpl._originalCommerceCountryId = commerceRegionModelImpl._commerceCountryId;

		commerceRegionModelImpl._setOriginalCommerceCountryId = false;

		commerceRegionModelImpl._originalActive = commerceRegionModelImpl._active;

		commerceRegionModelImpl._setOriginalActive = false;

		commerceRegionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CommerceRegion> toCacheModel() {
		CommerceRegionCacheModel commerceRegionCacheModel = new CommerceRegionCacheModel();

		commerceRegionCacheModel.uuid = getUuid();

		String uuid = commerceRegionCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			commerceRegionCacheModel.uuid = null;
		}

		commerceRegionCacheModel.commerceRegionId = getCommerceRegionId();

		commerceRegionCacheModel.groupId = getGroupId();

		commerceRegionCacheModel.companyId = getCompanyId();

		commerceRegionCacheModel.userId = getUserId();

		commerceRegionCacheModel.userName = getUserName();

		String userName = commerceRegionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceRegionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceRegionCacheModel.createDate = createDate.getTime();
		}
		else {
			commerceRegionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceRegionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			commerceRegionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceRegionCacheModel.commerceCountryId = getCommerceCountryId();

		commerceRegionCacheModel.name = getName();

		String name = commerceRegionCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			commerceRegionCacheModel.name = null;
		}

		commerceRegionCacheModel.code = getCode();

		String code = commerceRegionCacheModel.code;

		if ((code != null) && (code.length() == 0)) {
			commerceRegionCacheModel.code = null;
		}

		commerceRegionCacheModel.priority = getPriority();

		commerceRegionCacheModel.active = getActive();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			commerceRegionCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			commerceRegionCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return commerceRegionCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", commerceRegionId=");
		sb.append(getCommerceRegionId());
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
		sb.append(", commerceCountryId=");
		sb.append(getCommerceCountryId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", code=");
		sb.append(getCode());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append(", active=");
		sb.append(getActive());
		sb.append(", lastPublishDate=");
		sb.append(getLastPublishDate());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(46);

		sb.append("<model><model-name>");
		sb.append("com.liferay.commerce.address.model.CommerceRegion");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>commerceRegionId</column-name><column-value><![CDATA[");
		sb.append(getCommerceRegionId());
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
			"<column><column-name>commerceCountryId</column-name><column-value><![CDATA[");
		sb.append(getCommerceCountryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>code</column-name><column-value><![CDATA[");
		sb.append(getCode());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>active</column-name><column-value><![CDATA[");
		sb.append(getActive());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPublishDate</column-name><column-value><![CDATA[");
		sb.append(getLastPublishDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CommerceRegion.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CommerceRegion.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _commerceRegionId;
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
	private long _commerceCountryId;
	private long _originalCommerceCountryId;
	private boolean _setOriginalCommerceCountryId;
	private String _name;
	private String _code;
	private double _priority;
	private boolean _active;
	private boolean _originalActive;
	private boolean _setOriginalActive;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private CommerceRegion _escapedModel;
}