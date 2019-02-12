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

package com.liferay.oauth.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.model.OAuthApplicationModel;
import com.liferay.oauth.model.OAuthApplicationSoap;

import com.liferay.petra.string.StringBundler;

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

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the OAuthApplication service. Represents a row in the &quot;OAuth_OAuthApplication&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link OAuthApplicationModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link OAuthApplicationImpl}.
 * </p>
 *
 * @author Ivica Cardic
 * @see OAuthApplicationImpl
 * @see OAuthApplication
 * @see OAuthApplicationModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class OAuthApplicationModelImpl extends BaseModelImpl<OAuthApplication>
	implements OAuthApplicationModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a o auth application model instance should use the {@link OAuthApplication} interface instead.
	 */
	public static final String TABLE_NAME = "OAuth_OAuthApplication";
	public static final Object[][] TABLE_COLUMNS = {
			{ "oAuthApplicationId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "name", Types.VARCHAR },
			{ "description_", Types.VARCHAR },
			{ "consumerKey", Types.VARCHAR },
			{ "consumerSecret", Types.VARCHAR },
			{ "accessLevel", Types.INTEGER },
			{ "logoId", Types.BIGINT },
			{ "shareableAccessToken", Types.BOOLEAN },
			{ "callbackURI", Types.VARCHAR },
			{ "websiteURL", Types.VARCHAR }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("oAuthApplicationId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("consumerKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("consumerSecret", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("accessLevel", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("logoId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("shareableAccessToken", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("callbackURI", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("websiteURL", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE = "create table OAuth_OAuthApplication (oAuthApplicationId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,description_ STRING null,consumerKey VARCHAR(75) null,consumerSecret VARCHAR(75) null,accessLevel INTEGER,logoId LONG,shareableAccessToken BOOLEAN,callbackURI STRING null,websiteURL STRING null)";
	public static final String TABLE_SQL_DROP = "drop table OAuth_OAuthApplication";
	public static final String ORDER_BY_JPQL = " ORDER BY oAuthApplication.oAuthApplicationId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY OAuth_OAuthApplication.oAuthApplicationId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.oauth.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.oauth.model.OAuthApplication"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.oauth.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.oauth.model.OAuthApplication"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.oauth.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.oauth.model.OAuthApplication"),
			true);
	public static final long COMPANYID_COLUMN_BITMASK = 1L;
	public static final long CONSUMERKEY_COLUMN_BITMASK = 2L;
	public static final long NAME_COLUMN_BITMASK = 4L;
	public static final long USERID_COLUMN_BITMASK = 8L;
	public static final long OAUTHAPPLICATIONID_COLUMN_BITMASK = 16L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static OAuthApplication toModel(OAuthApplicationSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		OAuthApplication model = new OAuthApplicationImpl();

		model.setOAuthApplicationId(soapModel.getOAuthApplicationId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setConsumerKey(soapModel.getConsumerKey());
		model.setConsumerSecret(soapModel.getConsumerSecret());
		model.setAccessLevel(soapModel.getAccessLevel());
		model.setLogoId(soapModel.getLogoId());
		model.setShareableAccessToken(soapModel.isShareableAccessToken());
		model.setCallbackURI(soapModel.getCallbackURI());
		model.setWebsiteURL(soapModel.getWebsiteURL());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<OAuthApplication> toModels(
		OAuthApplicationSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<OAuthApplication> models = new ArrayList<OAuthApplication>(soapModels.length);

		for (OAuthApplicationSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.oauth.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.oauth.model.OAuthApplication"));

	public OAuthApplicationModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _oAuthApplicationId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setOAuthApplicationId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _oAuthApplicationId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return OAuthApplication.class;
	}

	@Override
	public String getModelClassName() {
		return OAuthApplication.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<OAuthApplication, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<OAuthApplication, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<OAuthApplication, Object> attributeGetterFunction = entry.getValue();

			attributes.put(attributeName,
				attributeGetterFunction.apply((OAuthApplication)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<OAuthApplication, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<OAuthApplication, Object> attributeSetterBiConsumer = attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept((OAuthApplication)this,
					entry.getValue());
			}
		}
	}

	public Map<String, Function<OAuthApplication, Object>> getAttributeGetterFunctions() {
		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<OAuthApplication, Object>> getAttributeSetterBiConsumers() {
		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<OAuthApplication, Object>> _attributeGetterFunctions;
	private static final Map<String, BiConsumer<OAuthApplication, Object>> _attributeSetterBiConsumers;

	static {
		Map<String, Function<OAuthApplication, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<OAuthApplication, Object>>();
		Map<String, BiConsumer<OAuthApplication, ?>> attributeSetterBiConsumers = new LinkedHashMap<String, BiConsumer<OAuthApplication, ?>>();


		attributeGetterFunctions.put("oAuthApplicationId", OAuthApplication::getOAuthApplicationId);
		attributeSetterBiConsumers.put("oAuthApplicationId", (BiConsumer<OAuthApplication, Long>)OAuthApplication::setOAuthApplicationId);
		attributeGetterFunctions.put("companyId", OAuthApplication::getCompanyId);
		attributeSetterBiConsumers.put("companyId", (BiConsumer<OAuthApplication, Long>)OAuthApplication::setCompanyId);
		attributeGetterFunctions.put("userId", OAuthApplication::getUserId);
		attributeSetterBiConsumers.put("userId", (BiConsumer<OAuthApplication, Long>)OAuthApplication::setUserId);
		attributeGetterFunctions.put("userName", OAuthApplication::getUserName);
		attributeSetterBiConsumers.put("userName", (BiConsumer<OAuthApplication, String>)OAuthApplication::setUserName);
		attributeGetterFunctions.put("createDate", OAuthApplication::getCreateDate);
		attributeSetterBiConsumers.put("createDate", (BiConsumer<OAuthApplication, Date>)OAuthApplication::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", OAuthApplication::getModifiedDate);
		attributeSetterBiConsumers.put("modifiedDate", (BiConsumer<OAuthApplication, Date>)OAuthApplication::setModifiedDate);
		attributeGetterFunctions.put("name", OAuthApplication::getName);
		attributeSetterBiConsumers.put("name", (BiConsumer<OAuthApplication, String>)OAuthApplication::setName);
		attributeGetterFunctions.put("description", OAuthApplication::getDescription);
		attributeSetterBiConsumers.put("description", (BiConsumer<OAuthApplication, String>)OAuthApplication::setDescription);
		attributeGetterFunctions.put("consumerKey", OAuthApplication::getConsumerKey);
		attributeSetterBiConsumers.put("consumerKey", (BiConsumer<OAuthApplication, String>)OAuthApplication::setConsumerKey);
		attributeGetterFunctions.put("consumerSecret", OAuthApplication::getConsumerSecret);
		attributeSetterBiConsumers.put("consumerSecret", (BiConsumer<OAuthApplication, String>)OAuthApplication::setConsumerSecret);
		attributeGetterFunctions.put("accessLevel", OAuthApplication::getAccessLevel);
		attributeSetterBiConsumers.put("accessLevel", (BiConsumer<OAuthApplication, Integer>)OAuthApplication::setAccessLevel);
		attributeGetterFunctions.put("logoId", OAuthApplication::getLogoId);
		attributeSetterBiConsumers.put("logoId", (BiConsumer<OAuthApplication, Long>)OAuthApplication::setLogoId);
		attributeGetterFunctions.put("shareableAccessToken", OAuthApplication::getShareableAccessToken);
		attributeSetterBiConsumers.put("shareableAccessToken", (BiConsumer<OAuthApplication, Boolean>)OAuthApplication::setShareableAccessToken);
		attributeGetterFunctions.put("callbackURI", OAuthApplication::getCallbackURI);
		attributeSetterBiConsumers.put("callbackURI", (BiConsumer<OAuthApplication, String>)OAuthApplication::setCallbackURI);
		attributeGetterFunctions.put("websiteURL", OAuthApplication::getWebsiteURL);
		attributeSetterBiConsumers.put("websiteURL", (BiConsumer<OAuthApplication, String>)OAuthApplication::setWebsiteURL);


		_attributeGetterFunctions = Collections.unmodifiableMap(attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap((Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getOAuthApplicationId() {
		return _oAuthApplicationId;
	}

	@Override
	public void setOAuthApplicationId(long oAuthApplicationId) {
		_oAuthApplicationId = oAuthApplicationId;
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
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

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

	public long getOriginalUserId() {
		return _originalUserId;
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
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_columnBitmask |= NAME_COLUMN_BITMASK;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	@JSON
	@Override
	public String getDescription() {
		if (_description == null) {
			return "";
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@JSON
	@Override
	public String getConsumerKey() {
		if (_consumerKey == null) {
			return "";
		}
		else {
			return _consumerKey;
		}
	}

	@Override
	public void setConsumerKey(String consumerKey) {
		_columnBitmask |= CONSUMERKEY_COLUMN_BITMASK;

		if (_originalConsumerKey == null) {
			_originalConsumerKey = _consumerKey;
		}

		_consumerKey = consumerKey;
	}

	public String getOriginalConsumerKey() {
		return GetterUtil.getString(_originalConsumerKey);
	}

	@JSON
	@Override
	public String getConsumerSecret() {
		if (_consumerSecret == null) {
			return "";
		}
		else {
			return _consumerSecret;
		}
	}

	@Override
	public void setConsumerSecret(String consumerSecret) {
		_consumerSecret = consumerSecret;
	}

	@JSON
	@Override
	public int getAccessLevel() {
		return _accessLevel;
	}

	@Override
	public void setAccessLevel(int accessLevel) {
		_accessLevel = accessLevel;
	}

	@JSON
	@Override
	public long getLogoId() {
		return _logoId;
	}

	@Override
	public void setLogoId(long logoId) {
		_logoId = logoId;
	}

	@JSON
	@Override
	public boolean getShareableAccessToken() {
		return _shareableAccessToken;
	}

	@JSON
	@Override
	public boolean isShareableAccessToken() {
		return _shareableAccessToken;
	}

	@Override
	public void setShareableAccessToken(boolean shareableAccessToken) {
		_shareableAccessToken = shareableAccessToken;
	}

	@JSON
	@Override
	public String getCallbackURI() {
		if (_callbackURI == null) {
			return "";
		}
		else {
			return _callbackURI;
		}
	}

	@Override
	public void setCallbackURI(String callbackURI) {
		_callbackURI = callbackURI;
	}

	@JSON
	@Override
	public String getWebsiteURL() {
		if (_websiteURL == null) {
			return "";
		}
		else {
			return _websiteURL;
		}
	}

	@Override
	public void setWebsiteURL(String websiteURL) {
		_websiteURL = websiteURL;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			OAuthApplication.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public OAuthApplication toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (OAuthApplication)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		OAuthApplicationImpl oAuthApplicationImpl = new OAuthApplicationImpl();

		oAuthApplicationImpl.setOAuthApplicationId(getOAuthApplicationId());
		oAuthApplicationImpl.setCompanyId(getCompanyId());
		oAuthApplicationImpl.setUserId(getUserId());
		oAuthApplicationImpl.setUserName(getUserName());
		oAuthApplicationImpl.setCreateDate(getCreateDate());
		oAuthApplicationImpl.setModifiedDate(getModifiedDate());
		oAuthApplicationImpl.setName(getName());
		oAuthApplicationImpl.setDescription(getDescription());
		oAuthApplicationImpl.setConsumerKey(getConsumerKey());
		oAuthApplicationImpl.setConsumerSecret(getConsumerSecret());
		oAuthApplicationImpl.setAccessLevel(getAccessLevel());
		oAuthApplicationImpl.setLogoId(getLogoId());
		oAuthApplicationImpl.setShareableAccessToken(isShareableAccessToken());
		oAuthApplicationImpl.setCallbackURI(getCallbackURI());
		oAuthApplicationImpl.setWebsiteURL(getWebsiteURL());

		oAuthApplicationImpl.resetOriginalValues();

		return oAuthApplicationImpl;
	}

	@Override
	public int compareTo(OAuthApplication oAuthApplication) {
		long primaryKey = oAuthApplication.getPrimaryKey();

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

		if (!(obj instanceof OAuthApplication)) {
			return false;
		}

		OAuthApplication oAuthApplication = (OAuthApplication)obj;

		long primaryKey = oAuthApplication.getPrimaryKey();

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
		OAuthApplicationModelImpl oAuthApplicationModelImpl = this;

		oAuthApplicationModelImpl._originalCompanyId = oAuthApplicationModelImpl._companyId;

		oAuthApplicationModelImpl._setOriginalCompanyId = false;

		oAuthApplicationModelImpl._originalUserId = oAuthApplicationModelImpl._userId;

		oAuthApplicationModelImpl._setOriginalUserId = false;

		oAuthApplicationModelImpl._setModifiedDate = false;

		oAuthApplicationModelImpl._originalName = oAuthApplicationModelImpl._name;

		oAuthApplicationModelImpl._originalConsumerKey = oAuthApplicationModelImpl._consumerKey;

		oAuthApplicationModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<OAuthApplication> toCacheModel() {
		OAuthApplicationCacheModel oAuthApplicationCacheModel = new OAuthApplicationCacheModel();

		oAuthApplicationCacheModel.oAuthApplicationId = getOAuthApplicationId();

		oAuthApplicationCacheModel.companyId = getCompanyId();

		oAuthApplicationCacheModel.userId = getUserId();

		oAuthApplicationCacheModel.userName = getUserName();

		String userName = oAuthApplicationCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			oAuthApplicationCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			oAuthApplicationCacheModel.createDate = createDate.getTime();
		}
		else {
			oAuthApplicationCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			oAuthApplicationCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			oAuthApplicationCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		oAuthApplicationCacheModel.name = getName();

		String name = oAuthApplicationCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			oAuthApplicationCacheModel.name = null;
		}

		oAuthApplicationCacheModel.description = getDescription();

		String description = oAuthApplicationCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			oAuthApplicationCacheModel.description = null;
		}

		oAuthApplicationCacheModel.consumerKey = getConsumerKey();

		String consumerKey = oAuthApplicationCacheModel.consumerKey;

		if ((consumerKey != null) && (consumerKey.length() == 0)) {
			oAuthApplicationCacheModel.consumerKey = null;
		}

		oAuthApplicationCacheModel.consumerSecret = getConsumerSecret();

		String consumerSecret = oAuthApplicationCacheModel.consumerSecret;

		if ((consumerSecret != null) && (consumerSecret.length() == 0)) {
			oAuthApplicationCacheModel.consumerSecret = null;
		}

		oAuthApplicationCacheModel.accessLevel = getAccessLevel();

		oAuthApplicationCacheModel.logoId = getLogoId();

		oAuthApplicationCacheModel.shareableAccessToken = isShareableAccessToken();

		oAuthApplicationCacheModel.callbackURI = getCallbackURI();

		String callbackURI = oAuthApplicationCacheModel.callbackURI;

		if ((callbackURI != null) && (callbackURI.length() == 0)) {
			oAuthApplicationCacheModel.callbackURI = null;
		}

		oAuthApplicationCacheModel.websiteURL = getWebsiteURL();

		String websiteURL = oAuthApplicationCacheModel.websiteURL;

		if ((websiteURL != null) && (websiteURL.length() == 0)) {
			oAuthApplicationCacheModel.websiteURL = null;
		}

		return oAuthApplicationCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<OAuthApplication, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((4 * attributeGetterFunctions.size()) +
				2);

		sb.append("{");

		for (Map.Entry<String, Function<OAuthApplication, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<OAuthApplication, Object> attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((OAuthApplication)this));
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
		Map<String, Function<OAuthApplication, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((5 * attributeGetterFunctions.size()) +
				4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<OAuthApplication, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<OAuthApplication, Object> attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((OAuthApplication)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = OAuthApplication.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			OAuthApplication.class, ModelWrapper.class
		};
	private long _oAuthApplicationId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _name;
	private String _originalName;
	private String _description;
	private String _consumerKey;
	private String _originalConsumerKey;
	private String _consumerSecret;
	private int _accessLevel;
	private long _logoId;
	private boolean _shareableAccessToken;
	private String _callbackURI;
	private String _websiteURL;
	private long _columnBitmask;
	private OAuthApplication _escapedModel;
}