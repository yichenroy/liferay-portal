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

package com.liferay.saml.persistence.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.model.SamlSpSessionModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the SamlSpSession service. Represents a row in the &quot;SamlSpSession&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link SamlSpSessionModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SamlSpSessionImpl}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpSessionImpl
 * @see SamlSpSession
 * @see SamlSpSessionModel
 * @generated
 */
@ProviderType
public class SamlSpSessionModelImpl extends BaseModelImpl<SamlSpSession>
	implements SamlSpSessionModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a saml sp session model instance should use the {@link SamlSpSession} interface instead.
	 */
	public static final String TABLE_NAME = "SamlSpSession";
	public static final Object[][] TABLE_COLUMNS = {
			{ "samlSpSessionId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "samlSpSessionKey", Types.VARCHAR },
			{ "assertionXml", Types.CLOB },
			{ "jSessionId", Types.VARCHAR },
			{ "nameIdFormat", Types.VARCHAR },
			{ "nameIdNameQualifier", Types.VARCHAR },
			{ "nameIdSPNameQualifier", Types.VARCHAR },
			{ "nameIdValue", Types.VARCHAR },
			{ "sessionIndex", Types.VARCHAR },
			{ "terminated_", Types.BOOLEAN }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("samlSpSessionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("samlSpSessionKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("assertionXml", Types.CLOB);
		TABLE_COLUMNS_MAP.put("jSessionId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("nameIdFormat", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("nameIdNameQualifier", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("nameIdSPNameQualifier", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("nameIdValue", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("sessionIndex", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("terminated_", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE = "create table SamlSpSession (samlSpSessionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,samlSpSessionKey VARCHAR(75) null,assertionXml TEXT null,jSessionId VARCHAR(200) null,nameIdFormat VARCHAR(1024) null,nameIdNameQualifier VARCHAR(1024) null,nameIdSPNameQualifier VARCHAR(1024) null,nameIdValue VARCHAR(1024) null,sessionIndex VARCHAR(75) null,terminated_ BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table SamlSpSession";
	public static final String ORDER_BY_JPQL = " ORDER BY samlSpSession.samlSpSessionId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY SamlSpSession.samlSpSessionId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.saml.persistence.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.saml.persistence.model.SamlSpSession"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.saml.persistence.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.saml.persistence.model.SamlSpSession"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.saml.persistence.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.saml.persistence.model.SamlSpSession"),
			true);
	public static final long JSESSIONID_COLUMN_BITMASK = 1L;
	public static final long NAMEIDVALUE_COLUMN_BITMASK = 2L;
	public static final long SAMLSPSESSIONKEY_COLUMN_BITMASK = 4L;
	public static final long SESSIONINDEX_COLUMN_BITMASK = 8L;
	public static final long SAMLSPSESSIONID_COLUMN_BITMASK = 16L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.saml.persistence.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.saml.persistence.model.SamlSpSession"));

	public SamlSpSessionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _samlSpSessionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSamlSpSessionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _samlSpSessionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SamlSpSession.class;
	}

	@Override
	public String getModelClassName() {
		return SamlSpSession.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("samlSpSessionId", getSamlSpSessionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("samlSpSessionKey", getSamlSpSessionKey());
		attributes.put("assertionXml", getAssertionXml());
		attributes.put("jSessionId", getJSessionId());
		attributes.put("nameIdFormat", getNameIdFormat());
		attributes.put("nameIdNameQualifier", getNameIdNameQualifier());
		attributes.put("nameIdSPNameQualifier", getNameIdSPNameQualifier());
		attributes.put("nameIdValue", getNameIdValue());
		attributes.put("sessionIndex", getSessionIndex());
		attributes.put("terminated", isTerminated());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long samlSpSessionId = (Long)attributes.get("samlSpSessionId");

		if (samlSpSessionId != null) {
			setSamlSpSessionId(samlSpSessionId);
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

		String samlSpSessionKey = (String)attributes.get("samlSpSessionKey");

		if (samlSpSessionKey != null) {
			setSamlSpSessionKey(samlSpSessionKey);
		}

		String assertionXml = (String)attributes.get("assertionXml");

		if (assertionXml != null) {
			setAssertionXml(assertionXml);
		}

		String jSessionId = (String)attributes.get("jSessionId");

		if (jSessionId != null) {
			setJSessionId(jSessionId);
		}

		String nameIdFormat = (String)attributes.get("nameIdFormat");

		if (nameIdFormat != null) {
			setNameIdFormat(nameIdFormat);
		}

		String nameIdNameQualifier = (String)attributes.get(
				"nameIdNameQualifier");

		if (nameIdNameQualifier != null) {
			setNameIdNameQualifier(nameIdNameQualifier);
		}

		String nameIdSPNameQualifier = (String)attributes.get(
				"nameIdSPNameQualifier");

		if (nameIdSPNameQualifier != null) {
			setNameIdSPNameQualifier(nameIdSPNameQualifier);
		}

		String nameIdValue = (String)attributes.get("nameIdValue");

		if (nameIdValue != null) {
			setNameIdValue(nameIdValue);
		}

		String sessionIndex = (String)attributes.get("sessionIndex");

		if (sessionIndex != null) {
			setSessionIndex(sessionIndex);
		}

		Boolean terminated = (Boolean)attributes.get("terminated");

		if (terminated != null) {
			setTerminated(terminated);
		}
	}

	@Override
	public long getSamlSpSessionId() {
		return _samlSpSessionId;
	}

	@Override
	public void setSamlSpSessionId(long samlSpSessionId) {
		_samlSpSessionId = samlSpSessionId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
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
	public String getSamlSpSessionKey() {
		if (_samlSpSessionKey == null) {
			return "";
		}
		else {
			return _samlSpSessionKey;
		}
	}

	@Override
	public void setSamlSpSessionKey(String samlSpSessionKey) {
		_columnBitmask |= SAMLSPSESSIONKEY_COLUMN_BITMASK;

		if (_originalSamlSpSessionKey == null) {
			_originalSamlSpSessionKey = _samlSpSessionKey;
		}

		_samlSpSessionKey = samlSpSessionKey;
	}

	public String getOriginalSamlSpSessionKey() {
		return GetterUtil.getString(_originalSamlSpSessionKey);
	}

	@Override
	public String getAssertionXml() {
		if (_assertionXml == null) {
			return "";
		}
		else {
			return _assertionXml;
		}
	}

	@Override
	public void setAssertionXml(String assertionXml) {
		_assertionXml = assertionXml;
	}

	@Override
	public String getJSessionId() {
		if (_jSessionId == null) {
			return "";
		}
		else {
			return _jSessionId;
		}
	}

	@Override
	public void setJSessionId(String jSessionId) {
		_columnBitmask |= JSESSIONID_COLUMN_BITMASK;

		if (_originalJSessionId == null) {
			_originalJSessionId = _jSessionId;
		}

		_jSessionId = jSessionId;
	}

	public String getOriginalJSessionId() {
		return GetterUtil.getString(_originalJSessionId);
	}

	@Override
	public String getNameIdFormat() {
		if (_nameIdFormat == null) {
			return "";
		}
		else {
			return _nameIdFormat;
		}
	}

	@Override
	public void setNameIdFormat(String nameIdFormat) {
		_nameIdFormat = nameIdFormat;
	}

	@Override
	public String getNameIdNameQualifier() {
		if (_nameIdNameQualifier == null) {
			return "";
		}
		else {
			return _nameIdNameQualifier;
		}
	}

	@Override
	public void setNameIdNameQualifier(String nameIdNameQualifier) {
		_nameIdNameQualifier = nameIdNameQualifier;
	}

	@Override
	public String getNameIdSPNameQualifier() {
		if (_nameIdSPNameQualifier == null) {
			return "";
		}
		else {
			return _nameIdSPNameQualifier;
		}
	}

	@Override
	public void setNameIdSPNameQualifier(String nameIdSPNameQualifier) {
		_nameIdSPNameQualifier = nameIdSPNameQualifier;
	}

	@Override
	public String getNameIdValue() {
		if (_nameIdValue == null) {
			return "";
		}
		else {
			return _nameIdValue;
		}
	}

	@Override
	public void setNameIdValue(String nameIdValue) {
		_columnBitmask |= NAMEIDVALUE_COLUMN_BITMASK;

		if (_originalNameIdValue == null) {
			_originalNameIdValue = _nameIdValue;
		}

		_nameIdValue = nameIdValue;
	}

	public String getOriginalNameIdValue() {
		return GetterUtil.getString(_originalNameIdValue);
	}

	@Override
	public String getSessionIndex() {
		if (_sessionIndex == null) {
			return "";
		}
		else {
			return _sessionIndex;
		}
	}

	@Override
	public void setSessionIndex(String sessionIndex) {
		_columnBitmask |= SESSIONINDEX_COLUMN_BITMASK;

		if (_originalSessionIndex == null) {
			_originalSessionIndex = _sessionIndex;
		}

		_sessionIndex = sessionIndex;
	}

	public String getOriginalSessionIndex() {
		return GetterUtil.getString(_originalSessionIndex);
	}

	@Override
	public boolean getTerminated() {
		return _terminated;
	}

	@Override
	public boolean isTerminated() {
		return _terminated;
	}

	@Override
	public void setTerminated(boolean terminated) {
		_terminated = terminated;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			SamlSpSession.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public SamlSpSession toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (SamlSpSession)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		SamlSpSessionImpl samlSpSessionImpl = new SamlSpSessionImpl();

		samlSpSessionImpl.setSamlSpSessionId(getSamlSpSessionId());
		samlSpSessionImpl.setCompanyId(getCompanyId());
		samlSpSessionImpl.setUserId(getUserId());
		samlSpSessionImpl.setUserName(getUserName());
		samlSpSessionImpl.setCreateDate(getCreateDate());
		samlSpSessionImpl.setModifiedDate(getModifiedDate());
		samlSpSessionImpl.setSamlSpSessionKey(getSamlSpSessionKey());
		samlSpSessionImpl.setAssertionXml(getAssertionXml());
		samlSpSessionImpl.setJSessionId(getJSessionId());
		samlSpSessionImpl.setNameIdFormat(getNameIdFormat());
		samlSpSessionImpl.setNameIdNameQualifier(getNameIdNameQualifier());
		samlSpSessionImpl.setNameIdSPNameQualifier(getNameIdSPNameQualifier());
		samlSpSessionImpl.setNameIdValue(getNameIdValue());
		samlSpSessionImpl.setSessionIndex(getSessionIndex());
		samlSpSessionImpl.setTerminated(isTerminated());

		samlSpSessionImpl.resetOriginalValues();

		return samlSpSessionImpl;
	}

	@Override
	public int compareTo(SamlSpSession samlSpSession) {
		long primaryKey = samlSpSession.getPrimaryKey();

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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlSpSession)) {
			return false;
		}

		SamlSpSession samlSpSession = (SamlSpSession)obj;

		long primaryKey = samlSpSession.getPrimaryKey();

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
		SamlSpSessionModelImpl samlSpSessionModelImpl = this;

		samlSpSessionModelImpl._setModifiedDate = false;

		samlSpSessionModelImpl._originalSamlSpSessionKey = samlSpSessionModelImpl._samlSpSessionKey;

		samlSpSessionModelImpl._originalJSessionId = samlSpSessionModelImpl._jSessionId;

		samlSpSessionModelImpl._originalNameIdValue = samlSpSessionModelImpl._nameIdValue;

		samlSpSessionModelImpl._originalSessionIndex = samlSpSessionModelImpl._sessionIndex;

		samlSpSessionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<SamlSpSession> toCacheModel() {
		SamlSpSessionCacheModel samlSpSessionCacheModel = new SamlSpSessionCacheModel();

		samlSpSessionCacheModel.samlSpSessionId = getSamlSpSessionId();

		samlSpSessionCacheModel.companyId = getCompanyId();

		samlSpSessionCacheModel.userId = getUserId();

		samlSpSessionCacheModel.userName = getUserName();

		String userName = samlSpSessionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			samlSpSessionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			samlSpSessionCacheModel.createDate = createDate.getTime();
		}
		else {
			samlSpSessionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			samlSpSessionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			samlSpSessionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		samlSpSessionCacheModel.samlSpSessionKey = getSamlSpSessionKey();

		String samlSpSessionKey = samlSpSessionCacheModel.samlSpSessionKey;

		if ((samlSpSessionKey != null) && (samlSpSessionKey.length() == 0)) {
			samlSpSessionCacheModel.samlSpSessionKey = null;
		}

		samlSpSessionCacheModel.assertionXml = getAssertionXml();

		String assertionXml = samlSpSessionCacheModel.assertionXml;

		if ((assertionXml != null) && (assertionXml.length() == 0)) {
			samlSpSessionCacheModel.assertionXml = null;
		}

		samlSpSessionCacheModel.jSessionId = getJSessionId();

		String jSessionId = samlSpSessionCacheModel.jSessionId;

		if ((jSessionId != null) && (jSessionId.length() == 0)) {
			samlSpSessionCacheModel.jSessionId = null;
		}

		samlSpSessionCacheModel.nameIdFormat = getNameIdFormat();

		String nameIdFormat = samlSpSessionCacheModel.nameIdFormat;

		if ((nameIdFormat != null) && (nameIdFormat.length() == 0)) {
			samlSpSessionCacheModel.nameIdFormat = null;
		}

		samlSpSessionCacheModel.nameIdNameQualifier = getNameIdNameQualifier();

		String nameIdNameQualifier = samlSpSessionCacheModel.nameIdNameQualifier;

		if ((nameIdNameQualifier != null) &&
				(nameIdNameQualifier.length() == 0)) {
			samlSpSessionCacheModel.nameIdNameQualifier = null;
		}

		samlSpSessionCacheModel.nameIdSPNameQualifier = getNameIdSPNameQualifier();

		String nameIdSPNameQualifier = samlSpSessionCacheModel.nameIdSPNameQualifier;

		if ((nameIdSPNameQualifier != null) &&
				(nameIdSPNameQualifier.length() == 0)) {
			samlSpSessionCacheModel.nameIdSPNameQualifier = null;
		}

		samlSpSessionCacheModel.nameIdValue = getNameIdValue();

		String nameIdValue = samlSpSessionCacheModel.nameIdValue;

		if ((nameIdValue != null) && (nameIdValue.length() == 0)) {
			samlSpSessionCacheModel.nameIdValue = null;
		}

		samlSpSessionCacheModel.sessionIndex = getSessionIndex();

		String sessionIndex = samlSpSessionCacheModel.sessionIndex;

		if ((sessionIndex != null) && (sessionIndex.length() == 0)) {
			samlSpSessionCacheModel.sessionIndex = null;
		}

		samlSpSessionCacheModel.terminated = isTerminated();

		return samlSpSessionCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{samlSpSessionId=");
		sb.append(getSamlSpSessionId());
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
		sb.append(", samlSpSessionKey=");
		sb.append(getSamlSpSessionKey());
		sb.append(", assertionXml=");
		sb.append(getAssertionXml());
		sb.append(", jSessionId=");
		sb.append(getJSessionId());
		sb.append(", nameIdFormat=");
		sb.append(getNameIdFormat());
		sb.append(", nameIdNameQualifier=");
		sb.append(getNameIdNameQualifier());
		sb.append(", nameIdSPNameQualifier=");
		sb.append(getNameIdSPNameQualifier());
		sb.append(", nameIdValue=");
		sb.append(getNameIdValue());
		sb.append(", sessionIndex=");
		sb.append(getSessionIndex());
		sb.append(", terminated=");
		sb.append(isTerminated());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(49);

		sb.append("<model><model-name>");
		sb.append("com.liferay.saml.persistence.model.SamlSpSession");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>samlSpSessionId</column-name><column-value><![CDATA[");
		sb.append(getSamlSpSessionId());
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
			"<column><column-name>samlSpSessionKey</column-name><column-value><![CDATA[");
		sb.append(getSamlSpSessionKey());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>assertionXml</column-name><column-value><![CDATA[");
		sb.append(getAssertionXml());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>jSessionId</column-name><column-value><![CDATA[");
		sb.append(getJSessionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>nameIdFormat</column-name><column-value><![CDATA[");
		sb.append(getNameIdFormat());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>nameIdNameQualifier</column-name><column-value><![CDATA[");
		sb.append(getNameIdNameQualifier());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>nameIdSPNameQualifier</column-name><column-value><![CDATA[");
		sb.append(getNameIdSPNameQualifier());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>nameIdValue</column-name><column-value><![CDATA[");
		sb.append(getNameIdValue());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sessionIndex</column-name><column-value><![CDATA[");
		sb.append(getSessionIndex());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>terminated</column-name><column-value><![CDATA[");
		sb.append(isTerminated());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = SamlSpSession.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			SamlSpSession.class
		};
	private long _samlSpSessionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _samlSpSessionKey;
	private String _originalSamlSpSessionKey;
	private String _assertionXml;
	private String _jSessionId;
	private String _originalJSessionId;
	private String _nameIdFormat;
	private String _nameIdNameQualifier;
	private String _nameIdSPNameQualifier;
	private String _nameIdValue;
	private String _originalNameIdValue;
	private String _sessionIndex;
	private String _originalSessionIndex;
	private boolean _terminated;
	private long _columnBitmask;
	private SamlSpSession _escapedModel;
}