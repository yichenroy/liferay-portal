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

package com.liferay.commerce.discount.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.model.CommerceDiscountRelModel;
import com.liferay.commerce.discount.model.CommerceDiscountRelSoap;

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
 * The base model implementation for the CommerceDiscountRel service. Represents a row in the &quot;CommerceDiscountRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CommerceDiscountRelModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceDiscountRelImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountRelImpl
 * @see CommerceDiscountRel
 * @see CommerceDiscountRelModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class CommerceDiscountRelModelImpl extends BaseModelImpl<CommerceDiscountRel>
	implements CommerceDiscountRelModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce discount rel model instance should use the {@link CommerceDiscountRel} interface instead.
	 */
	public static final String TABLE_NAME = "CommerceDiscountRel";
	public static final Object[][] TABLE_COLUMNS = {
			{ "commerceDiscountRelId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "commerceDiscountId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("commerceDiscountRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("commerceDiscountId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE = "create table CommerceDiscountRel (commerceDiscountRelId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceDiscountId LONG,classNameId LONG,classPK LONG)";
	public static final String TABLE_SQL_DROP = "drop table CommerceDiscountRel";
	public static final String ORDER_BY_JPQL = " ORDER BY commerceDiscountRel.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY CommerceDiscountRel.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.discount.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.discount.model.CommerceDiscountRel"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.discount.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.discount.model.CommerceDiscountRel"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.discount.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.discount.model.CommerceDiscountRel"),
			true);
	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static final long CLASSPK_COLUMN_BITMASK = 2L;
	public static final long COMMERCEDISCOUNTID_COLUMN_BITMASK = 4L;
	public static final long CREATEDATE_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CommerceDiscountRel toModel(CommerceDiscountRelSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CommerceDiscountRel model = new CommerceDiscountRelImpl();

		model.setCommerceDiscountRelId(soapModel.getCommerceDiscountRelId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCommerceDiscountId(soapModel.getCommerceDiscountId());
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
	public static List<CommerceDiscountRel> toModels(
		CommerceDiscountRelSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CommerceDiscountRel> models = new ArrayList<CommerceDiscountRel>(soapModels.length);

		for (CommerceDiscountRelSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.discount.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.discount.model.CommerceDiscountRel"));

	public CommerceDiscountRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceDiscountRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceDiscountRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceDiscountRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceDiscountRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceDiscountRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceDiscountRelId", getCommerceDiscountRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceDiscountId", getCommerceDiscountId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceDiscountRelId = (Long)attributes.get(
				"commerceDiscountRelId");

		if (commerceDiscountRelId != null) {
			setCommerceDiscountRelId(commerceDiscountRelId);
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

		Long commerceDiscountId = (Long)attributes.get("commerceDiscountId");

		if (commerceDiscountId != null) {
			setCommerceDiscountId(commerceDiscountId);
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
	public long getCommerceDiscountRelId() {
		return _commerceDiscountRelId;
	}

	@Override
	public void setCommerceDiscountRelId(long commerceDiscountRelId) {
		_commerceDiscountRelId = commerceDiscountRelId;
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
	public long getCommerceDiscountId() {
		return _commerceDiscountId;
	}

	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		_columnBitmask |= COMMERCEDISCOUNTID_COLUMN_BITMASK;

		if (!_setOriginalCommerceDiscountId) {
			_setOriginalCommerceDiscountId = true;

			_originalCommerceDiscountId = _commerceDiscountId;
		}

		_commerceDiscountId = commerceDiscountId;
	}

	public long getOriginalCommerceDiscountId() {
		return _originalCommerceDiscountId;
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
			CommerceDiscountRel.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceDiscountRel toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CommerceDiscountRel)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CommerceDiscountRelImpl commerceDiscountRelImpl = new CommerceDiscountRelImpl();

		commerceDiscountRelImpl.setCommerceDiscountRelId(getCommerceDiscountRelId());
		commerceDiscountRelImpl.setGroupId(getGroupId());
		commerceDiscountRelImpl.setCompanyId(getCompanyId());
		commerceDiscountRelImpl.setUserId(getUserId());
		commerceDiscountRelImpl.setUserName(getUserName());
		commerceDiscountRelImpl.setCreateDate(getCreateDate());
		commerceDiscountRelImpl.setModifiedDate(getModifiedDate());
		commerceDiscountRelImpl.setCommerceDiscountId(getCommerceDiscountId());
		commerceDiscountRelImpl.setClassNameId(getClassNameId());
		commerceDiscountRelImpl.setClassPK(getClassPK());

		commerceDiscountRelImpl.resetOriginalValues();

		return commerceDiscountRelImpl;
	}

	@Override
	public int compareTo(CommerceDiscountRel commerceDiscountRel) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				commerceDiscountRel.getCreateDate());

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

		if (!(obj instanceof CommerceDiscountRel)) {
			return false;
		}

		CommerceDiscountRel commerceDiscountRel = (CommerceDiscountRel)obj;

		long primaryKey = commerceDiscountRel.getPrimaryKey();

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
		CommerceDiscountRelModelImpl commerceDiscountRelModelImpl = this;

		commerceDiscountRelModelImpl._setModifiedDate = false;

		commerceDiscountRelModelImpl._originalCommerceDiscountId = commerceDiscountRelModelImpl._commerceDiscountId;

		commerceDiscountRelModelImpl._setOriginalCommerceDiscountId = false;

		commerceDiscountRelModelImpl._originalClassNameId = commerceDiscountRelModelImpl._classNameId;

		commerceDiscountRelModelImpl._setOriginalClassNameId = false;

		commerceDiscountRelModelImpl._originalClassPK = commerceDiscountRelModelImpl._classPK;

		commerceDiscountRelModelImpl._setOriginalClassPK = false;

		commerceDiscountRelModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CommerceDiscountRel> toCacheModel() {
		CommerceDiscountRelCacheModel commerceDiscountRelCacheModel = new CommerceDiscountRelCacheModel();

		commerceDiscountRelCacheModel.commerceDiscountRelId = getCommerceDiscountRelId();

		commerceDiscountRelCacheModel.groupId = getGroupId();

		commerceDiscountRelCacheModel.companyId = getCompanyId();

		commerceDiscountRelCacheModel.userId = getUserId();

		commerceDiscountRelCacheModel.userName = getUserName();

		String userName = commerceDiscountRelCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceDiscountRelCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceDiscountRelCacheModel.createDate = createDate.getTime();
		}
		else {
			commerceDiscountRelCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceDiscountRelCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			commerceDiscountRelCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceDiscountRelCacheModel.commerceDiscountId = getCommerceDiscountId();

		commerceDiscountRelCacheModel.classNameId = getClassNameId();

		commerceDiscountRelCacheModel.classPK = getClassPK();

		return commerceDiscountRelCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceDiscountRelId=");
		sb.append(getCommerceDiscountRelId());
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
		sb.append(", commerceDiscountId=");
		sb.append(getCommerceDiscountId());
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
		sb.append("com.liferay.commerce.discount.model.CommerceDiscountRel");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>commerceDiscountRelId</column-name><column-value><![CDATA[");
		sb.append(getCommerceDiscountRelId());
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
			"<column><column-name>commerceDiscountId</column-name><column-value><![CDATA[");
		sb.append(getCommerceDiscountId());
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

	private static final ClassLoader _classLoader = CommerceDiscountRel.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CommerceDiscountRel.class, ModelWrapper.class
		};
	private long _commerceDiscountRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _commerceDiscountId;
	private long _originalCommerceDiscountId;
	private boolean _setOriginalCommerceDiscountId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _columnBitmask;
	private CommerceDiscountRel _escapedModel;
}