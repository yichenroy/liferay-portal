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

package com.liferay.commerce.price.list.qualification.type.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelModel;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRelSoap;

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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the CommercePriceListUserRel service. Represents a row in the &quot;CommercePriceListUserRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CommercePriceListUserRelModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommercePriceListUserRelImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserRelImpl
 * @see CommercePriceListUserRel
 * @see CommercePriceListUserRelModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class CommercePriceListUserRelModelImpl extends BaseModelImpl<CommercePriceListUserRel>
	implements CommercePriceListUserRelModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce price list user rel model instance should use the {@link CommercePriceListUserRel} interface instead.
	 */
	public static final String TABLE_NAME = "CommercePriceListUserRel";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "commercePriceListUserRelId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "CPLQualificationTypeRelId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "lastPublishDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("commercePriceListUserRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("CPLQualificationTypeRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table CommercePriceListUserRel (uuid_ VARCHAR(75) null,commercePriceListUserRelId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CPLQualificationTypeRelId LONG,classNameId LONG,classPK LONG,lastPublishDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table CommercePriceListUserRel";
	public static final String ORDER_BY_JPQL = " ORDER BY commercePriceListUserRel.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY CommercePriceListUserRel.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.price.list.qualification.type.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.price.list.qualification.type.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.price.list.qualification.type.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel"),
			true);
	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static final long CLASSPK_COLUMN_BITMASK = 2L;
	public static final long COMMERCEPRICELISTQUALIFICATIONTYPERELID_COLUMN_BITMASK =
		4L;
	public static final long COMPANYID_COLUMN_BITMASK = 8L;
	public static final long GROUPID_COLUMN_BITMASK = 16L;
	public static final long UUID_COLUMN_BITMASK = 32L;
	public static final long CREATEDATE_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CommercePriceListUserRel toModel(
		CommercePriceListUserRelSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CommercePriceListUserRel model = new CommercePriceListUserRelImpl();

		model.setUuid(soapModel.getUuid());
		model.setCommercePriceListUserRelId(soapModel.getCommercePriceListUserRelId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCommercePriceListQualificationTypeRelId(soapModel.getCommercePriceListQualificationTypeRelId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CommercePriceListUserRel> toModels(
		CommercePriceListUserRelSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CommercePriceListUserRel> models = new ArrayList<CommercePriceListUserRel>(soapModels.length);

		for (CommercePriceListUserRelSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.price.list.qualification.type.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel"));

	public CommercePriceListUserRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commercePriceListUserRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommercePriceListUserRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceListUserRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceListUserRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceListUserRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commercePriceListUserRelId",
			getCommercePriceListUserRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceListQualificationTypeRelId",
			getCommercePriceListQualificationTypeRelId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
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

		Long commercePriceListUserRelId = (Long)attributes.get(
				"commercePriceListUserRelId");

		if (commercePriceListUserRelId != null) {
			setCommercePriceListUserRelId(commercePriceListUserRelId);
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

		Long commercePriceListQualificationTypeRelId = (Long)attributes.get(
				"commercePriceListQualificationTypeRelId");

		if (commercePriceListQualificationTypeRelId != null) {
			setCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
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
			return "";
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
	public long getCommercePriceListUserRelId() {
		return _commercePriceListUserRelId;
	}

	@Override
	public void setCommercePriceListUserRelId(long commercePriceListUserRelId) {
		_commercePriceListUserRelId = commercePriceListUserRelId;
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
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

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
	public long getCommercePriceListQualificationTypeRelId() {
		return _commercePriceListQualificationTypeRelId;
	}

	@Override
	public void setCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId) {
		_columnBitmask |= COMMERCEPRICELISTQUALIFICATIONTYPERELID_COLUMN_BITMASK;

		if (!_setOriginalCommercePriceListQualificationTypeRelId) {
			_setOriginalCommercePriceListQualificationTypeRelId = true;

			_originalCommercePriceListQualificationTypeRelId = _commercePriceListQualificationTypeRelId;
		}

		_commercePriceListQualificationTypeRelId = commercePriceListQualificationTypeRelId;
	}

	public long getOriginalCommercePriceListQualificationTypeRelId() {
		return _originalCommercePriceListQualificationTypeRelId;
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

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
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
				CommercePriceListUserRel.class.getName()), getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CommercePriceListUserRel.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommercePriceListUserRel toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CommercePriceListUserRel)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CommercePriceListUserRelImpl commercePriceListUserRelImpl = new CommercePriceListUserRelImpl();

		commercePriceListUserRelImpl.setUuid(getUuid());
		commercePriceListUserRelImpl.setCommercePriceListUserRelId(getCommercePriceListUserRelId());
		commercePriceListUserRelImpl.setGroupId(getGroupId());
		commercePriceListUserRelImpl.setCompanyId(getCompanyId());
		commercePriceListUserRelImpl.setUserId(getUserId());
		commercePriceListUserRelImpl.setUserName(getUserName());
		commercePriceListUserRelImpl.setCreateDate(getCreateDate());
		commercePriceListUserRelImpl.setModifiedDate(getModifiedDate());
		commercePriceListUserRelImpl.setCommercePriceListQualificationTypeRelId(getCommercePriceListQualificationTypeRelId());
		commercePriceListUserRelImpl.setClassNameId(getClassNameId());
		commercePriceListUserRelImpl.setClassPK(getClassPK());
		commercePriceListUserRelImpl.setLastPublishDate(getLastPublishDate());

		commercePriceListUserRelImpl.resetOriginalValues();

		return commercePriceListUserRelImpl;
	}

	@Override
	public int compareTo(CommercePriceListUserRel commercePriceListUserRel) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				commercePriceListUserRel.getCreateDate());

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

		if (!(obj instanceof CommercePriceListUserRel)) {
			return false;
		}

		CommercePriceListUserRel commercePriceListUserRel = (CommercePriceListUserRel)obj;

		long primaryKey = commercePriceListUserRel.getPrimaryKey();

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
		CommercePriceListUserRelModelImpl commercePriceListUserRelModelImpl = this;

		commercePriceListUserRelModelImpl._originalUuid = commercePriceListUserRelModelImpl._uuid;

		commercePriceListUserRelModelImpl._originalGroupId = commercePriceListUserRelModelImpl._groupId;

		commercePriceListUserRelModelImpl._setOriginalGroupId = false;

		commercePriceListUserRelModelImpl._originalCompanyId = commercePriceListUserRelModelImpl._companyId;

		commercePriceListUserRelModelImpl._setOriginalCompanyId = false;

		commercePriceListUserRelModelImpl._setModifiedDate = false;

		commercePriceListUserRelModelImpl._originalCommercePriceListQualificationTypeRelId = commercePriceListUserRelModelImpl._commercePriceListQualificationTypeRelId;

		commercePriceListUserRelModelImpl._setOriginalCommercePriceListQualificationTypeRelId = false;

		commercePriceListUserRelModelImpl._originalClassNameId = commercePriceListUserRelModelImpl._classNameId;

		commercePriceListUserRelModelImpl._setOriginalClassNameId = false;

		commercePriceListUserRelModelImpl._originalClassPK = commercePriceListUserRelModelImpl._classPK;

		commercePriceListUserRelModelImpl._setOriginalClassPK = false;

		commercePriceListUserRelModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CommercePriceListUserRel> toCacheModel() {
		CommercePriceListUserRelCacheModel commercePriceListUserRelCacheModel = new CommercePriceListUserRelCacheModel();

		commercePriceListUserRelCacheModel.uuid = getUuid();

		String uuid = commercePriceListUserRelCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			commercePriceListUserRelCacheModel.uuid = null;
		}

		commercePriceListUserRelCacheModel.commercePriceListUserRelId = getCommercePriceListUserRelId();

		commercePriceListUserRelCacheModel.groupId = getGroupId();

		commercePriceListUserRelCacheModel.companyId = getCompanyId();

		commercePriceListUserRelCacheModel.userId = getUserId();

		commercePriceListUserRelCacheModel.userName = getUserName();

		String userName = commercePriceListUserRelCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commercePriceListUserRelCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commercePriceListUserRelCacheModel.createDate = createDate.getTime();
		}
		else {
			commercePriceListUserRelCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commercePriceListUserRelCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			commercePriceListUserRelCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commercePriceListUserRelCacheModel.commercePriceListQualificationTypeRelId = getCommercePriceListQualificationTypeRelId();

		commercePriceListUserRelCacheModel.classNameId = getClassNameId();

		commercePriceListUserRelCacheModel.classPK = getClassPK();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			commercePriceListUserRelCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			commercePriceListUserRelCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return commercePriceListUserRelCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", commercePriceListUserRelId=");
		sb.append(getCommercePriceListUserRelId());
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
		sb.append(", commercePriceListQualificationTypeRelId=");
		sb.append(getCommercePriceListQualificationTypeRelId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", lastPublishDate=");
		sb.append(getLastPublishDate());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append(
			"com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>commercePriceListUserRelId</column-name><column-value><![CDATA[");
		sb.append(getCommercePriceListUserRelId());
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
			"<column><column-name>commercePriceListQualificationTypeRelId</column-name><column-value><![CDATA[");
		sb.append(getCommercePriceListQualificationTypeRelId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPublishDate</column-name><column-value><![CDATA[");
		sb.append(getLastPublishDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CommercePriceListUserRel.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CommercePriceListUserRel.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _commercePriceListUserRelId;
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
	private long _commercePriceListQualificationTypeRelId;
	private long _originalCommercePriceListQualificationTypeRelId;
	private boolean _setOriginalCommercePriceListQualificationTypeRelId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private CommercePriceListUserRel _escapedModel;
}