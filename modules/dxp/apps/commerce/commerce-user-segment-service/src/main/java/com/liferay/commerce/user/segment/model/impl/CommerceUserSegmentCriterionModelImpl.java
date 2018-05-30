/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.segment.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionModel;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
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

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the CommerceUserSegmentCriterion service. Represents a row in the &quot;CommerceUserSegmentCriterion&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CommerceUserSegmentCriterionModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceUserSegmentCriterionImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterionImpl
 * @see CommerceUserSegmentCriterion
 * @see CommerceUserSegmentCriterionModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class CommerceUserSegmentCriterionModelImpl extends BaseModelImpl<CommerceUserSegmentCriterion>
	implements CommerceUserSegmentCriterionModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce user segment criterion model instance should use the {@link CommerceUserSegmentCriterion} interface instead.
	 */
	public static final String TABLE_NAME = "CommerceUserSegmentCriterion";
	public static final Object[][] TABLE_COLUMNS = {
			{ "commerceUserSegmentCriterionId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "commerceUserSegmentEntryId", Types.BIGINT },
			{ "type_", Types.VARCHAR },
			{ "typeSettings", Types.CLOB },
			{ "priority", Types.DOUBLE }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("commerceUserSegmentCriterionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("commerceUserSegmentEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);
		TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);
	}

	public static final String TABLE_SQL_CREATE = "create table CommerceUserSegmentCriterion (commerceUserSegmentCriterionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceUserSegmentEntryId LONG,type_ VARCHAR(75) null,typeSettings TEXT null,priority DOUBLE)";
	public static final String TABLE_SQL_DROP = "drop table CommerceUserSegmentCriterion";
	public static final String ORDER_BY_JPQL = " ORDER BY commerceUserSegmentCriterion.priority ASC";
	public static final String ORDER_BY_SQL = " ORDER BY CommerceUserSegmentCriterion.priority ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.user.segment.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.user.segment.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.user.segment.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion"),
			true);
	public static final long COMMERCEUSERSEGMENTENTRYID_COLUMN_BITMASK = 1L;
	public static final long PRIORITY_COLUMN_BITMASK = 2L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CommerceUserSegmentCriterion toModel(
		CommerceUserSegmentCriterionSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CommerceUserSegmentCriterion model = new CommerceUserSegmentCriterionImpl();

		model.setCommerceUserSegmentCriterionId(soapModel.getCommerceUserSegmentCriterionId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCommerceUserSegmentEntryId(soapModel.getCommerceUserSegmentEntryId());
		model.setType(soapModel.getType());
		model.setTypeSettings(soapModel.getTypeSettings());
		model.setPriority(soapModel.getPriority());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CommerceUserSegmentCriterion> toModels(
		CommerceUserSegmentCriterionSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CommerceUserSegmentCriterion> models = new ArrayList<CommerceUserSegmentCriterion>(soapModels.length);

		for (CommerceUserSegmentCriterionSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.user.segment.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion"));

	public CommerceUserSegmentCriterionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceUserSegmentCriterionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceUserSegmentCriterionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceUserSegmentCriterionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceUserSegmentCriterion.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceUserSegmentCriterion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceUserSegmentCriterionId",
			getCommerceUserSegmentCriterionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceUserSegmentEntryId",
			getCommerceUserSegmentEntryId());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("priority", getPriority());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceUserSegmentCriterionId = (Long)attributes.get(
				"commerceUserSegmentCriterionId");

		if (commerceUserSegmentCriterionId != null) {
			setCommerceUserSegmentCriterionId(commerceUserSegmentCriterionId);
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

		Long commerceUserSegmentEntryId = (Long)attributes.get(
				"commerceUserSegmentEntryId");

		if (commerceUserSegmentEntryId != null) {
			setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@JSON
	@Override
	public long getCommerceUserSegmentCriterionId() {
		return _commerceUserSegmentCriterionId;
	}

	@Override
	public void setCommerceUserSegmentCriterionId(
		long commerceUserSegmentCriterionId) {
		_commerceUserSegmentCriterionId = commerceUserSegmentCriterionId;
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
	public long getCommerceUserSegmentEntryId() {
		return _commerceUserSegmentEntryId;
	}

	@Override
	public void setCommerceUserSegmentEntryId(long commerceUserSegmentEntryId) {
		_columnBitmask |= COMMERCEUSERSEGMENTENTRYID_COLUMN_BITMASK;

		if (!_setOriginalCommerceUserSegmentEntryId) {
			_setOriginalCommerceUserSegmentEntryId = true;

			_originalCommerceUserSegmentEntryId = _commerceUserSegmentEntryId;
		}

		_commerceUserSegmentEntryId = commerceUserSegmentEntryId;
	}

	public long getOriginalCommerceUserSegmentEntryId() {
		return _originalCommerceUserSegmentEntryId;
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
		_type = type;
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
		_typeSettings = typeSettings;
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

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CommerceUserSegmentCriterion.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceUserSegmentCriterion toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CommerceUserSegmentCriterion)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CommerceUserSegmentCriterionImpl commerceUserSegmentCriterionImpl = new CommerceUserSegmentCriterionImpl();

		commerceUserSegmentCriterionImpl.setCommerceUserSegmentCriterionId(getCommerceUserSegmentCriterionId());
		commerceUserSegmentCriterionImpl.setGroupId(getGroupId());
		commerceUserSegmentCriterionImpl.setCompanyId(getCompanyId());
		commerceUserSegmentCriterionImpl.setUserId(getUserId());
		commerceUserSegmentCriterionImpl.setUserName(getUserName());
		commerceUserSegmentCriterionImpl.setCreateDate(getCreateDate());
		commerceUserSegmentCriterionImpl.setModifiedDate(getModifiedDate());
		commerceUserSegmentCriterionImpl.setCommerceUserSegmentEntryId(getCommerceUserSegmentEntryId());
		commerceUserSegmentCriterionImpl.setType(getType());
		commerceUserSegmentCriterionImpl.setTypeSettings(getTypeSettings());
		commerceUserSegmentCriterionImpl.setPriority(getPriority());

		commerceUserSegmentCriterionImpl.resetOriginalValues();

		return commerceUserSegmentCriterionImpl;
	}

	@Override
	public int compareTo(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		int value = 0;

		if (getPriority() < commerceUserSegmentCriterion.getPriority()) {
			value = -1;
		}
		else if (getPriority() > commerceUserSegmentCriterion.getPriority()) {
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

		if (!(obj instanceof CommerceUserSegmentCriterion)) {
			return false;
		}

		CommerceUserSegmentCriterion commerceUserSegmentCriterion = (CommerceUserSegmentCriterion)obj;

		long primaryKey = commerceUserSegmentCriterion.getPrimaryKey();

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
		CommerceUserSegmentCriterionModelImpl commerceUserSegmentCriterionModelImpl =
			this;

		commerceUserSegmentCriterionModelImpl._setModifiedDate = false;

		commerceUserSegmentCriterionModelImpl._originalCommerceUserSegmentEntryId = commerceUserSegmentCriterionModelImpl._commerceUserSegmentEntryId;

		commerceUserSegmentCriterionModelImpl._setOriginalCommerceUserSegmentEntryId = false;

		commerceUserSegmentCriterionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CommerceUserSegmentCriterion> toCacheModel() {
		CommerceUserSegmentCriterionCacheModel commerceUserSegmentCriterionCacheModel =
			new CommerceUserSegmentCriterionCacheModel();

		commerceUserSegmentCriterionCacheModel.commerceUserSegmentCriterionId = getCommerceUserSegmentCriterionId();

		commerceUserSegmentCriterionCacheModel.groupId = getGroupId();

		commerceUserSegmentCriterionCacheModel.companyId = getCompanyId();

		commerceUserSegmentCriterionCacheModel.userId = getUserId();

		commerceUserSegmentCriterionCacheModel.userName = getUserName();

		String userName = commerceUserSegmentCriterionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceUserSegmentCriterionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceUserSegmentCriterionCacheModel.createDate = createDate.getTime();
		}
		else {
			commerceUserSegmentCriterionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceUserSegmentCriterionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			commerceUserSegmentCriterionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceUserSegmentCriterionCacheModel.commerceUserSegmentEntryId = getCommerceUserSegmentEntryId();

		commerceUserSegmentCriterionCacheModel.type = getType();

		String type = commerceUserSegmentCriterionCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			commerceUserSegmentCriterionCacheModel.type = null;
		}

		commerceUserSegmentCriterionCacheModel.typeSettings = getTypeSettings();

		String typeSettings = commerceUserSegmentCriterionCacheModel.typeSettings;

		if ((typeSettings != null) && (typeSettings.length() == 0)) {
			commerceUserSegmentCriterionCacheModel.typeSettings = null;
		}

		commerceUserSegmentCriterionCacheModel.priority = getPriority();

		return commerceUserSegmentCriterionCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{commerceUserSegmentCriterionId=");
		sb.append(getCommerceUserSegmentCriterionId());
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
		sb.append(", commerceUserSegmentEntryId=");
		sb.append(getCommerceUserSegmentEntryId());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", typeSettings=");
		sb.append(getTypeSettings());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append(
			"com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>commerceUserSegmentCriterionId</column-name><column-value><![CDATA[");
		sb.append(getCommerceUserSegmentCriterionId());
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
			"<column><column-name>commerceUserSegmentEntryId</column-name><column-value><![CDATA[");
		sb.append(getCommerceUserSegmentEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typeSettings</column-name><column-value><![CDATA[");
		sb.append(getTypeSettings());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CommerceUserSegmentCriterion.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CommerceUserSegmentCriterion.class, ModelWrapper.class
		};
	private long _commerceUserSegmentCriterionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _commerceUserSegmentEntryId;
	private long _originalCommerceUserSegmentEntryId;
	private boolean _setOriginalCommerceUserSegmentEntryId;
	private String _type;
	private String _typeSettings;
	private double _priority;
	private long _columnBitmask;
	private CommerceUserSegmentCriterion _escapedModel;
}