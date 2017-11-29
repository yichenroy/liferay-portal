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

import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.model.CommerceCartItemModel;
import com.liferay.commerce.model.CommerceCartItemSoap;

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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the CommerceCartItem service. Represents a row in the &quot;CommerceCartItem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CommerceCartItemModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceCartItemImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartItemImpl
 * @see CommerceCartItem
 * @see CommerceCartItemModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class CommerceCartItemModelImpl extends BaseModelImpl<CommerceCartItem>
	implements CommerceCartItemModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce cart item model instance should use the {@link CommerceCartItem} interface instead.
	 */
	public static final String TABLE_NAME = "CommerceCartItem";
	public static final Object[][] TABLE_COLUMNS = {
			{ "commerceCartItemId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "commerceCartId", Types.BIGINT },
			{ "CPDefinitionId", Types.BIGINT },
			{ "CPInstanceId", Types.BIGINT },
			{ "quantity", Types.INTEGER },
			{ "json", Types.CLOB }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("commerceCartItemId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("commerceCartId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("CPDefinitionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("CPInstanceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("quantity", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("json", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE = "create table CommerceCartItem (commerceCartItemId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceCartId LONG,CPDefinitionId LONG,CPInstanceId LONG,quantity INTEGER,json TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table CommerceCartItem";
	public static final String ORDER_BY_JPQL = " ORDER BY commerceCartItem.createDate ASC";
	public static final String ORDER_BY_SQL = " ORDER BY CommerceCartItem.createDate ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.model.CommerceCartItem"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.model.CommerceCartItem"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.model.CommerceCartItem"),
			true);
	public static final long CPDEFINITIONID_COLUMN_BITMASK = 1L;
	public static final long CPINSTANCEID_COLUMN_BITMASK = 2L;
	public static final long COMMERCECARTID_COLUMN_BITMASK = 4L;
	public static final long CREATEDATE_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CommerceCartItem toModel(CommerceCartItemSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CommerceCartItem model = new CommerceCartItemImpl();

		model.setCommerceCartItemId(soapModel.getCommerceCartItemId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCommerceCartId(soapModel.getCommerceCartId());
		model.setCPDefinitionId(soapModel.getCPDefinitionId());
		model.setCPInstanceId(soapModel.getCPInstanceId());
		model.setQuantity(soapModel.getQuantity());
		model.setJson(soapModel.getJson());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CommerceCartItem> toModels(
		CommerceCartItemSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CommerceCartItem> models = new ArrayList<CommerceCartItem>(soapModels.length);

		for (CommerceCartItemSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.model.CommerceCartItem"));

	public CommerceCartItemModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceCartItemId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceCartItemId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceCartItemId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceCartItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceCartItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceCartItemId", getCommerceCartItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceCartId", getCommerceCartId());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CPInstanceId", getCPInstanceId());
		attributes.put("quantity", getQuantity());
		attributes.put("json", getJson());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceCartItemId = (Long)attributes.get("commerceCartItemId");

		if (commerceCartItemId != null) {
			setCommerceCartItemId(commerceCartItemId);
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

		Long commerceCartId = (Long)attributes.get("commerceCartId");

		if (commerceCartId != null) {
			setCommerceCartId(commerceCartId);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long CPInstanceId = (Long)attributes.get("CPInstanceId");

		if (CPInstanceId != null) {
			setCPInstanceId(CPInstanceId);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJson(json);
		}
	}

	@JSON
	@Override
	public long getCommerceCartItemId() {
		return _commerceCartItemId;
	}

	@Override
	public void setCommerceCartItemId(long commerceCartItemId) {
		_commerceCartItemId = commerceCartItemId;
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
	public long getCommerceCartId() {
		return _commerceCartId;
	}

	@Override
	public void setCommerceCartId(long commerceCartId) {
		_columnBitmask |= COMMERCECARTID_COLUMN_BITMASK;

		if (!_setOriginalCommerceCartId) {
			_setOriginalCommerceCartId = true;

			_originalCommerceCartId = _commerceCartId;
		}

		_commerceCartId = commerceCartId;
	}

	public long getOriginalCommerceCartId() {
		return _originalCommerceCartId;
	}

	@JSON
	@Override
	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_columnBitmask |= CPDEFINITIONID_COLUMN_BITMASK;

		if (!_setOriginalCPDefinitionId) {
			_setOriginalCPDefinitionId = true;

			_originalCPDefinitionId = _CPDefinitionId;
		}

		_CPDefinitionId = CPDefinitionId;
	}

	public long getOriginalCPDefinitionId() {
		return _originalCPDefinitionId;
	}

	@JSON
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

	@JSON
	@Override
	public int getQuantity() {
		return _quantity;
	}

	@Override
	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	@JSON
	@Override
	public String getJson() {
		if (_json == null) {
			return "";
		}
		else {
			return _json;
		}
	}

	@Override
	public void setJson(String json) {
		_json = json;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CommerceCartItem.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceCartItem toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CommerceCartItem)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CommerceCartItemImpl commerceCartItemImpl = new CommerceCartItemImpl();

		commerceCartItemImpl.setCommerceCartItemId(getCommerceCartItemId());
		commerceCartItemImpl.setGroupId(getGroupId());
		commerceCartItemImpl.setCompanyId(getCompanyId());
		commerceCartItemImpl.setUserId(getUserId());
		commerceCartItemImpl.setUserName(getUserName());
		commerceCartItemImpl.setCreateDate(getCreateDate());
		commerceCartItemImpl.setModifiedDate(getModifiedDate());
		commerceCartItemImpl.setCommerceCartId(getCommerceCartId());
		commerceCartItemImpl.setCPDefinitionId(getCPDefinitionId());
		commerceCartItemImpl.setCPInstanceId(getCPInstanceId());
		commerceCartItemImpl.setQuantity(getQuantity());
		commerceCartItemImpl.setJson(getJson());

		commerceCartItemImpl.resetOriginalValues();

		return commerceCartItemImpl;
	}

	@Override
	public int compareTo(CommerceCartItem commerceCartItem) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				commerceCartItem.getCreateDate());

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

		if (!(obj instanceof CommerceCartItem)) {
			return false;
		}

		CommerceCartItem commerceCartItem = (CommerceCartItem)obj;

		long primaryKey = commerceCartItem.getPrimaryKey();

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
		CommerceCartItemModelImpl commerceCartItemModelImpl = this;

		commerceCartItemModelImpl._setModifiedDate = false;

		commerceCartItemModelImpl._originalCommerceCartId = commerceCartItemModelImpl._commerceCartId;

		commerceCartItemModelImpl._setOriginalCommerceCartId = false;

		commerceCartItemModelImpl._originalCPDefinitionId = commerceCartItemModelImpl._CPDefinitionId;

		commerceCartItemModelImpl._setOriginalCPDefinitionId = false;

		commerceCartItemModelImpl._originalCPInstanceId = commerceCartItemModelImpl._CPInstanceId;

		commerceCartItemModelImpl._setOriginalCPInstanceId = false;

		commerceCartItemModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CommerceCartItem> toCacheModel() {
		CommerceCartItemCacheModel commerceCartItemCacheModel = new CommerceCartItemCacheModel();

		commerceCartItemCacheModel.commerceCartItemId = getCommerceCartItemId();

		commerceCartItemCacheModel.groupId = getGroupId();

		commerceCartItemCacheModel.companyId = getCompanyId();

		commerceCartItemCacheModel.userId = getUserId();

		commerceCartItemCacheModel.userName = getUserName();

		String userName = commerceCartItemCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceCartItemCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceCartItemCacheModel.createDate = createDate.getTime();
		}
		else {
			commerceCartItemCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceCartItemCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			commerceCartItemCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceCartItemCacheModel.commerceCartId = getCommerceCartId();

		commerceCartItemCacheModel.CPDefinitionId = getCPDefinitionId();

		commerceCartItemCacheModel.CPInstanceId = getCPInstanceId();

		commerceCartItemCacheModel.quantity = getQuantity();

		commerceCartItemCacheModel.json = getJson();

		String json = commerceCartItemCacheModel.json;

		if ((json != null) && (json.length() == 0)) {
			commerceCartItemCacheModel.json = null;
		}

		return commerceCartItemCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{commerceCartItemId=");
		sb.append(getCommerceCartItemId());
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
		sb.append(", commerceCartId=");
		sb.append(getCommerceCartId());
		sb.append(", CPDefinitionId=");
		sb.append(getCPDefinitionId());
		sb.append(", CPInstanceId=");
		sb.append(getCPInstanceId());
		sb.append(", quantity=");
		sb.append(getQuantity());
		sb.append(", json=");
		sb.append(getJson());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append("com.liferay.commerce.model.CommerceCartItem");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>commerceCartItemId</column-name><column-value><![CDATA[");
		sb.append(getCommerceCartItemId());
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
			"<column><column-name>commerceCartId</column-name><column-value><![CDATA[");
		sb.append(getCommerceCartId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CPDefinitionId</column-name><column-value><![CDATA[");
		sb.append(getCPDefinitionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CPInstanceId</column-name><column-value><![CDATA[");
		sb.append(getCPInstanceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>quantity</column-name><column-value><![CDATA[");
		sb.append(getQuantity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>json</column-name><column-value><![CDATA[");
		sb.append(getJson());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CommerceCartItem.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CommerceCartItem.class
		};
	private long _commerceCartItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _commerceCartId;
	private long _originalCommerceCartId;
	private boolean _setOriginalCommerceCartId;
	private long _CPDefinitionId;
	private long _originalCPDefinitionId;
	private boolean _setOriginalCPDefinitionId;
	private long _CPInstanceId;
	private long _originalCPInstanceId;
	private boolean _setOriginalCPInstanceId;
	private int _quantity;
	private String _json;
	private long _columnBitmask;
	private CommerceCartItem _escapedModel;
}