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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceTaxCategoryRel;
import com.liferay.commerce.model.CommerceTaxCategoryRelModel;
import com.liferay.commerce.model.CommerceTaxCategoryRelSoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

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
 * The base model implementation for the CommerceTaxCategoryRel service. Represents a row in the &quot;CommerceTaxCategoryRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CommerceTaxCategoryRelModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceTaxCategoryRelImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryRelImpl
 * @see CommerceTaxCategoryRel
 * @see CommerceTaxCategoryRelModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class CommerceTaxCategoryRelModelImpl extends BaseModelImpl<CommerceTaxCategoryRel>
	implements CommerceTaxCategoryRelModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce tax category rel model instance should use the {@link CommerceTaxCategoryRel} interface instead.
	 */
	public static final String TABLE_NAME = "CommerceTaxCategoryRel";
	public static final Object[][] TABLE_COLUMNS = {
			{ "commerceTaxCategoryRelId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "commerceTaxCategoryId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("commerceTaxCategoryRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("commerceTaxCategoryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE = "create table CommerceTaxCategoryRel (commerceTaxCategoryRelId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceTaxCategoryId LONG,classNameId LONG,classPK LONG)";
	public static final String TABLE_SQL_DROP = "drop table CommerceTaxCategoryRel";
	public static final String ORDER_BY_JPQL = " ORDER BY commerceTaxCategoryRel.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY CommerceTaxCategoryRel.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.model.CommerceTaxCategoryRel"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.model.CommerceTaxCategoryRel"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.model.CommerceTaxCategoryRel"),
			true);
	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static final long CLASSPK_COLUMN_BITMASK = 2L;
	public static final long COMMERCETAXCATEGORYID_COLUMN_BITMASK = 4L;
	public static final long CREATEDATE_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CommerceTaxCategoryRel toModel(
		CommerceTaxCategoryRelSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CommerceTaxCategoryRel model = new CommerceTaxCategoryRelImpl();

		model.setCommerceTaxCategoryRelId(soapModel.getCommerceTaxCategoryRelId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCommerceTaxCategoryId(soapModel.getCommerceTaxCategoryId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CommerceTaxCategoryRel> toModels(
		CommerceTaxCategoryRelSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CommerceTaxCategoryRel> models = new ArrayList<CommerceTaxCategoryRel>(soapModels.length);

		for (CommerceTaxCategoryRelSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.model.CommerceTaxCategoryRel"));

	public CommerceTaxCategoryRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceTaxCategoryRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceTaxCategoryRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceTaxCategoryRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceTaxCategoryRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceTaxCategoryRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceTaxCategoryRelId", getCommerceTaxCategoryRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceTaxCategoryId", getCommerceTaxCategoryId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceTaxCategoryRelId = (Long)attributes.get(
				"commerceTaxCategoryRelId");

		if (commerceTaxCategoryRelId != null) {
			setCommerceTaxCategoryRelId(commerceTaxCategoryRelId);
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

		Long commerceTaxCategoryId = (Long)attributes.get(
				"commerceTaxCategoryId");

		if (commerceTaxCategoryId != null) {
			setCommerceTaxCategoryId(commerceTaxCategoryId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@JSON
	@Override
	public long getCommerceTaxCategoryRelId() {
		return _commerceTaxCategoryRelId;
	}

	@Override
	public void setCommerceTaxCategoryRelId(long commerceTaxCategoryRelId) {
		_commerceTaxCategoryRelId = commerceTaxCategoryRelId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
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
	public long getCommerceTaxCategoryId() {
		return _commerceTaxCategoryId;
	}

	@Override
	public void setCommerceTaxCategoryId(long commerceTaxCategoryId) {
		_columnBitmask |= COMMERCETAXCATEGORYID_COLUMN_BITMASK;

		if (!_setOriginalCommerceTaxCategoryId) {
			_setOriginalCommerceTaxCategoryId = true;

			_originalCommerceTaxCategoryId = _commerceTaxCategoryId;
		}

		_commerceTaxCategoryId = commerceTaxCategoryId;
	}

	public long getOriginalCommerceTaxCategoryId() {
		return _originalCommerceTaxCategoryId;
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

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CommerceTaxCategoryRel.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceTaxCategoryRel toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CommerceTaxCategoryRel)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CommerceTaxCategoryRelImpl commerceTaxCategoryRelImpl = new CommerceTaxCategoryRelImpl();

		commerceTaxCategoryRelImpl.setCommerceTaxCategoryRelId(getCommerceTaxCategoryRelId());
		commerceTaxCategoryRelImpl.setGroupId(getGroupId());
		commerceTaxCategoryRelImpl.setCompanyId(getCompanyId());
		commerceTaxCategoryRelImpl.setUserId(getUserId());
		commerceTaxCategoryRelImpl.setUserName(getUserName());
		commerceTaxCategoryRelImpl.setCreateDate(getCreateDate());
		commerceTaxCategoryRelImpl.setModifiedDate(getModifiedDate());
		commerceTaxCategoryRelImpl.setCommerceTaxCategoryId(getCommerceTaxCategoryId());
		commerceTaxCategoryRelImpl.setClassNameId(getClassNameId());
		commerceTaxCategoryRelImpl.setClassPK(getClassPK());

		commerceTaxCategoryRelImpl.resetOriginalValues();

		return commerceTaxCategoryRelImpl;
	}

	@Override
	public int compareTo(CommerceTaxCategoryRel commerceTaxCategoryRel) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				commerceTaxCategoryRel.getCreateDate());

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

		if (!(obj instanceof CommerceTaxCategoryRel)) {
			return false;
		}

		CommerceTaxCategoryRel commerceTaxCategoryRel = (CommerceTaxCategoryRel)obj;

		long primaryKey = commerceTaxCategoryRel.getPrimaryKey();

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
		CommerceTaxCategoryRelModelImpl commerceTaxCategoryRelModelImpl = this;

		commerceTaxCategoryRelModelImpl._setModifiedDate = false;

		commerceTaxCategoryRelModelImpl._originalCommerceTaxCategoryId = commerceTaxCategoryRelModelImpl._commerceTaxCategoryId;

		commerceTaxCategoryRelModelImpl._setOriginalCommerceTaxCategoryId = false;

		commerceTaxCategoryRelModelImpl._originalClassNameId = commerceTaxCategoryRelModelImpl._classNameId;

		commerceTaxCategoryRelModelImpl._setOriginalClassNameId = false;

		commerceTaxCategoryRelModelImpl._originalClassPK = commerceTaxCategoryRelModelImpl._classPK;

		commerceTaxCategoryRelModelImpl._setOriginalClassPK = false;

		commerceTaxCategoryRelModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CommerceTaxCategoryRel> toCacheModel() {
		CommerceTaxCategoryRelCacheModel commerceTaxCategoryRelCacheModel = new CommerceTaxCategoryRelCacheModel();

		commerceTaxCategoryRelCacheModel.commerceTaxCategoryRelId = getCommerceTaxCategoryRelId();

		commerceTaxCategoryRelCacheModel.groupId = getGroupId();

		commerceTaxCategoryRelCacheModel.companyId = getCompanyId();

		commerceTaxCategoryRelCacheModel.userId = getUserId();

		commerceTaxCategoryRelCacheModel.userName = getUserName();

		String userName = commerceTaxCategoryRelCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceTaxCategoryRelCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceTaxCategoryRelCacheModel.createDate = createDate.getTime();
		}
		else {
			commerceTaxCategoryRelCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceTaxCategoryRelCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			commerceTaxCategoryRelCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceTaxCategoryRelCacheModel.commerceTaxCategoryId = getCommerceTaxCategoryId();

		commerceTaxCategoryRelCacheModel.classNameId = getClassNameId();

		commerceTaxCategoryRelCacheModel.classPK = getClassPK();

		return commerceTaxCategoryRelCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceTaxCategoryRelId=");
		sb.append(getCommerceTaxCategoryRelId());
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
		sb.append(", commerceTaxCategoryId=");
		sb.append(getCommerceTaxCategoryId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.commerce.model.CommerceTaxCategoryRel");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>commerceTaxCategoryRelId</column-name><column-value><![CDATA[");
		sb.append(getCommerceTaxCategoryRelId());
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
			"<column><column-name>commerceTaxCategoryId</column-name><column-value><![CDATA[");
		sb.append(getCommerceTaxCategoryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CommerceTaxCategoryRel.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CommerceTaxCategoryRel.class
		};
	private long _commerceTaxCategoryRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _commerceTaxCategoryId;
	private long _originalCommerceTaxCategoryId;
	private boolean _setOriginalCommerceTaxCategoryId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _columnBitmask;
	private CommerceTaxCategoryRel _escapedModel;
}