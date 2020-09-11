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

package com.liferay.commerce.tax.engine.fixed.model.impl;

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelModel;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRelSoap;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

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
 * The base model implementation for the CommerceTaxFixedRateAddressRel service. Represents a row in the &quot;CommerceTaxFixedRateAddressRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>CommerceTaxFixedRateAddressRelModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceTaxFixedRateAddressRelImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelImpl
 * @generated
 */
@JSON(strict = true)
public class CommerceTaxFixedRateAddressRelModelImpl
	extends BaseModelImpl<CommerceTaxFixedRateAddressRel>
	implements CommerceTaxFixedRateAddressRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce tax fixed rate address rel model instance should use the <code>CommerceTaxFixedRateAddressRel</code> interface instead.
	 */
	public static final String TABLE_NAME = "CommerceTaxFixedRateAddressRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"CTaxFixedRateAddressRelId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"commerceTaxMethodId", Types.BIGINT},
		{"CPTaxCategoryId", Types.BIGINT}, {"commerceCountryId", Types.BIGINT},
		{"commerceRegionId", Types.BIGINT}, {"zip", Types.VARCHAR},
		{"rate", Types.DOUBLE}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("CTaxFixedRateAddressRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("commerceTaxMethodId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("CPTaxCategoryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("commerceCountryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("commerceRegionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("zip", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("rate", Types.DOUBLE);
	}

	public static final String TABLE_SQL_CREATE =
		"create table CommerceTaxFixedRateAddressRel (CTaxFixedRateAddressRelId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceTaxMethodId LONG,CPTaxCategoryId LONG,commerceCountryId LONG,commerceRegionId LONG,zip VARCHAR(75) null,rate DOUBLE)";

	public static final String TABLE_SQL_DROP =
		"drop table CommerceTaxFixedRateAddressRel";

	public static final String ORDER_BY_JPQL =
		" ORDER BY commerceTaxFixedRateAddressRel.createDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY CommerceTaxFixedRateAddressRel.createDate DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean ENTITY_CACHE_ENABLED = true;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean FINDER_CACHE_ENABLED = true;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean COLUMN_BITMASK_ENABLED = true;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CPTAXCATEGORYID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMMERCECOUNTRYID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMMERCETAXMETHODID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CREATEDATE_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static CommerceTaxFixedRateAddressRel toModel(
		CommerceTaxFixedRateAddressRelSoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		CommerceTaxFixedRateAddressRel model =
			new CommerceTaxFixedRateAddressRelImpl();

		model.setCommerceTaxFixedRateAddressRelId(
			soapModel.getCommerceTaxFixedRateAddressRelId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCommerceTaxMethodId(soapModel.getCommerceTaxMethodId());
		model.setCPTaxCategoryId(soapModel.getCPTaxCategoryId());
		model.setCommerceCountryId(soapModel.getCommerceCountryId());
		model.setCommerceRegionId(soapModel.getCommerceRegionId());
		model.setZip(soapModel.getZip());
		model.setRate(soapModel.getRate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static List<CommerceTaxFixedRateAddressRel> toModels(
		CommerceTaxFixedRateAddressRelSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<CommerceTaxFixedRateAddressRel> models =
			new ArrayList<CommerceTaxFixedRateAddressRel>(soapModels.length);

		for (CommerceTaxFixedRateAddressRelSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.commerce.tax.engine.fixed.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel"));

	public CommerceTaxFixedRateAddressRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceTaxFixedRateAddressRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceTaxFixedRateAddressRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceTaxFixedRateAddressRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceTaxFixedRateAddressRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceTaxFixedRateAddressRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<CommerceTaxFixedRateAddressRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<CommerceTaxFixedRateAddressRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceTaxFixedRateAddressRel, Object>
				attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply(
					(CommerceTaxFixedRateAddressRel)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<CommerceTaxFixedRateAddressRel, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<CommerceTaxFixedRateAddressRel, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(CommerceTaxFixedRateAddressRel)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<CommerceTaxFixedRateAddressRel, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<CommerceTaxFixedRateAddressRel, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, CommerceTaxFixedRateAddressRel>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			CommerceTaxFixedRateAddressRel.class.getClassLoader(),
			CommerceTaxFixedRateAddressRel.class, ModelWrapper.class);

		try {
			Constructor<CommerceTaxFixedRateAddressRel> constructor =
				(Constructor<CommerceTaxFixedRateAddressRel>)
					proxyClass.getConstructor(InvocationHandler.class);

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

	private static final Map
		<String, Function<CommerceTaxFixedRateAddressRel, Object>>
			_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<CommerceTaxFixedRateAddressRel, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<CommerceTaxFixedRateAddressRel, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String,
					 Function<CommerceTaxFixedRateAddressRel, Object>>();
		Map<String, BiConsumer<CommerceTaxFixedRateAddressRel, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<CommerceTaxFixedRateAddressRel, ?>>();

		attributeGetterFunctions.put(
			"commerceTaxFixedRateAddressRelId",
			CommerceTaxFixedRateAddressRel::
				getCommerceTaxFixedRateAddressRelId);
		attributeSetterBiConsumers.put(
			"commerceTaxFixedRateAddressRelId",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Long>)
				CommerceTaxFixedRateAddressRel::
					setCommerceTaxFixedRateAddressRelId);
		attributeGetterFunctions.put(
			"groupId", CommerceTaxFixedRateAddressRel::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Long>)
				CommerceTaxFixedRateAddressRel::setGroupId);
		attributeGetterFunctions.put(
			"companyId", CommerceTaxFixedRateAddressRel::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Long>)
				CommerceTaxFixedRateAddressRel::setCompanyId);
		attributeGetterFunctions.put(
			"userId", CommerceTaxFixedRateAddressRel::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Long>)
				CommerceTaxFixedRateAddressRel::setUserId);
		attributeGetterFunctions.put(
			"userName", CommerceTaxFixedRateAddressRel::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<CommerceTaxFixedRateAddressRel, String>)
				CommerceTaxFixedRateAddressRel::setUserName);
		attributeGetterFunctions.put(
			"createDate", CommerceTaxFixedRateAddressRel::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Date>)
				CommerceTaxFixedRateAddressRel::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", CommerceTaxFixedRateAddressRel::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Date>)
				CommerceTaxFixedRateAddressRel::setModifiedDate);
		attributeGetterFunctions.put(
			"commerceTaxMethodId",
			CommerceTaxFixedRateAddressRel::getCommerceTaxMethodId);
		attributeSetterBiConsumers.put(
			"commerceTaxMethodId",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Long>)
				CommerceTaxFixedRateAddressRel::setCommerceTaxMethodId);
		attributeGetterFunctions.put(
			"CPTaxCategoryId",
			CommerceTaxFixedRateAddressRel::getCPTaxCategoryId);
		attributeSetterBiConsumers.put(
			"CPTaxCategoryId",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Long>)
				CommerceTaxFixedRateAddressRel::setCPTaxCategoryId);
		attributeGetterFunctions.put(
			"commerceCountryId",
			CommerceTaxFixedRateAddressRel::getCommerceCountryId);
		attributeSetterBiConsumers.put(
			"commerceCountryId",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Long>)
				CommerceTaxFixedRateAddressRel::setCommerceCountryId);
		attributeGetterFunctions.put(
			"commerceRegionId",
			CommerceTaxFixedRateAddressRel::getCommerceRegionId);
		attributeSetterBiConsumers.put(
			"commerceRegionId",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Long>)
				CommerceTaxFixedRateAddressRel::setCommerceRegionId);
		attributeGetterFunctions.put(
			"zip", CommerceTaxFixedRateAddressRel::getZip);
		attributeSetterBiConsumers.put(
			"zip",
			(BiConsumer<CommerceTaxFixedRateAddressRel, String>)
				CommerceTaxFixedRateAddressRel::setZip);
		attributeGetterFunctions.put(
			"rate", CommerceTaxFixedRateAddressRel::getRate);
		attributeSetterBiConsumers.put(
			"rate",
			(BiConsumer<CommerceTaxFixedRateAddressRel, Double>)
				CommerceTaxFixedRateAddressRel::setRate);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getCommerceTaxFixedRateAddressRelId() {
		return _commerceTaxFixedRateAddressRelId;
	}

	@Override
	public void setCommerceTaxFixedRateAddressRelId(
		long commerceTaxFixedRateAddressRelId) {

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commerceTaxFixedRateAddressRelId = commerceTaxFixedRateAddressRelId;
	}

	@JSON
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

	@JSON
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

	@JSON
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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userName = userName;
	}

	@JSON
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

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getCommerceTaxMethodId() {
		return _commerceTaxMethodId;
	}

	@Override
	public void setCommerceTaxMethodId(long commerceTaxMethodId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commerceTaxMethodId = commerceTaxMethodId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCommerceTaxMethodId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("commerceTaxMethodId"));
	}

	@JSON
	@Override
	public long getCPTaxCategoryId() {
		return _CPTaxCategoryId;
	}

	@Override
	public void setCPTaxCategoryId(long CPTaxCategoryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_CPTaxCategoryId = CPTaxCategoryId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCPTaxCategoryId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("CPTaxCategoryId"));
	}

	@JSON
	@Override
	public long getCommerceCountryId() {
		return _commerceCountryId;
	}

	@Override
	public void setCommerceCountryId(long commerceCountryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commerceCountryId = commerceCountryId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCommerceCountryId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("commerceCountryId"));
	}

	@JSON
	@Override
	public long getCommerceRegionId() {
		return _commerceRegionId;
	}

	@Override
	public void setCommerceRegionId(long commerceRegionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commerceRegionId = commerceRegionId;
	}

	@JSON
	@Override
	public String getZip() {
		if (_zip == null) {
			return "";
		}
		else {
			return _zip;
		}
	}

	@Override
	public void setZip(String zip) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_zip = zip;
	}

	@JSON
	@Override
	public double getRate() {
		return _rate;
	}

	@Override
	public void setRate(double rate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_rate = rate;
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
			getCompanyId(), CommerceTaxFixedRateAddressRel.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceTaxFixedRateAddressRel toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, CommerceTaxFixedRateAddressRel>
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
		CommerceTaxFixedRateAddressRelImpl commerceTaxFixedRateAddressRelImpl =
			new CommerceTaxFixedRateAddressRelImpl();

		commerceTaxFixedRateAddressRelImpl.setCommerceTaxFixedRateAddressRelId(
			getCommerceTaxFixedRateAddressRelId());
		commerceTaxFixedRateAddressRelImpl.setGroupId(getGroupId());
		commerceTaxFixedRateAddressRelImpl.setCompanyId(getCompanyId());
		commerceTaxFixedRateAddressRelImpl.setUserId(getUserId());
		commerceTaxFixedRateAddressRelImpl.setUserName(getUserName());
		commerceTaxFixedRateAddressRelImpl.setCreateDate(getCreateDate());
		commerceTaxFixedRateAddressRelImpl.setModifiedDate(getModifiedDate());
		commerceTaxFixedRateAddressRelImpl.setCommerceTaxMethodId(
			getCommerceTaxMethodId());
		commerceTaxFixedRateAddressRelImpl.setCPTaxCategoryId(
			getCPTaxCategoryId());
		commerceTaxFixedRateAddressRelImpl.setCommerceCountryId(
			getCommerceCountryId());
		commerceTaxFixedRateAddressRelImpl.setCommerceRegionId(
			getCommerceRegionId());
		commerceTaxFixedRateAddressRelImpl.setZip(getZip());
		commerceTaxFixedRateAddressRelImpl.setRate(getRate());

		commerceTaxFixedRateAddressRelImpl.resetOriginalValues();

		return commerceTaxFixedRateAddressRelImpl;
	}

	@Override
	public int compareTo(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {

		int value = 0;

		value = DateUtil.compareTo(
			getCreateDate(), commerceTaxFixedRateAddressRel.getCreateDate());

		value = value * -1;

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

		if (!(object instanceof CommerceTaxFixedRateAddressRel)) {
			return false;
		}

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			(CommerceTaxFixedRateAddressRel)object;

		long primaryKey = commerceTaxFixedRateAddressRel.getPrimaryKey();

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
		return ENTITY_CACHE_ENABLED;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<CommerceTaxFixedRateAddressRel> toCacheModel() {
		CommerceTaxFixedRateAddressRelCacheModel
			commerceTaxFixedRateAddressRelCacheModel =
				new CommerceTaxFixedRateAddressRelCacheModel();

		commerceTaxFixedRateAddressRelCacheModel.
			commerceTaxFixedRateAddressRelId =
				getCommerceTaxFixedRateAddressRelId();

		commerceTaxFixedRateAddressRelCacheModel.groupId = getGroupId();

		commerceTaxFixedRateAddressRelCacheModel.companyId = getCompanyId();

		commerceTaxFixedRateAddressRelCacheModel.userId = getUserId();

		commerceTaxFixedRateAddressRelCacheModel.userName = getUserName();

		String userName = commerceTaxFixedRateAddressRelCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceTaxFixedRateAddressRelCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceTaxFixedRateAddressRelCacheModel.createDate =
				createDate.getTime();
		}
		else {
			commerceTaxFixedRateAddressRelCacheModel.createDate =
				Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceTaxFixedRateAddressRelCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			commerceTaxFixedRateAddressRelCacheModel.modifiedDate =
				Long.MIN_VALUE;
		}

		commerceTaxFixedRateAddressRelCacheModel.commerceTaxMethodId =
			getCommerceTaxMethodId();

		commerceTaxFixedRateAddressRelCacheModel.CPTaxCategoryId =
			getCPTaxCategoryId();

		commerceTaxFixedRateAddressRelCacheModel.commerceCountryId =
			getCommerceCountryId();

		commerceTaxFixedRateAddressRelCacheModel.commerceRegionId =
			getCommerceRegionId();

		commerceTaxFixedRateAddressRelCacheModel.zip = getZip();

		String zip = commerceTaxFixedRateAddressRelCacheModel.zip;

		if ((zip != null) && (zip.length() == 0)) {
			commerceTaxFixedRateAddressRelCacheModel.zip = null;
		}

		commerceTaxFixedRateAddressRelCacheModel.rate = getRate();

		return commerceTaxFixedRateAddressRelCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<CommerceTaxFixedRateAddressRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<CommerceTaxFixedRateAddressRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceTaxFixedRateAddressRel, Object>
				attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply(
					(CommerceTaxFixedRateAddressRel)this));
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
		Map<String, Function<CommerceTaxFixedRateAddressRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<CommerceTaxFixedRateAddressRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceTaxFixedRateAddressRel, Object>
				attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply(
					(CommerceTaxFixedRateAddressRel)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, CommerceTaxFixedRateAddressRel>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private long _commerceTaxFixedRateAddressRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _commerceTaxMethodId;
	private long _CPTaxCategoryId;
	private long _commerceCountryId;
	private long _commerceRegionId;
	private String _zip;
	private double _rate;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<CommerceTaxFixedRateAddressRel, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((CommerceTaxFixedRateAddressRel)this);
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

		_columnOriginalValues.put(
			"CTaxFixedRateAddressRelId", _commerceTaxFixedRateAddressRelId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("commerceTaxMethodId", _commerceTaxMethodId);
		_columnOriginalValues.put("CPTaxCategoryId", _CPTaxCategoryId);
		_columnOriginalValues.put("commerceCountryId", _commerceCountryId);
		_columnOriginalValues.put("commerceRegionId", _commerceRegionId);
		_columnOriginalValues.put("zip", _zip);
		_columnOriginalValues.put("rate", _rate);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put(
			"CTaxFixedRateAddressRelId", "commerceTaxFixedRateAddressRelId");

		_attributeNames = Collections.unmodifiableMap(attributeNames);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("CTaxFixedRateAddressRelId", 1L);

		columnBitmasks.put("groupId", 2L);

		columnBitmasks.put("companyId", 4L);

		columnBitmasks.put("userId", 8L);

		columnBitmasks.put("userName", 16L);

		columnBitmasks.put("createDate", 32L);

		columnBitmasks.put("modifiedDate", 64L);

		columnBitmasks.put("commerceTaxMethodId", 128L);

		columnBitmasks.put("CPTaxCategoryId", 256L);

		columnBitmasks.put("commerceCountryId", 512L);

		columnBitmasks.put("commerceRegionId", 1024L);

		columnBitmasks.put("zip", 2048L);

		columnBitmasks.put("rate", 4096L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private CommerceTaxFixedRateAddressRel _escapedModel;

}