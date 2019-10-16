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

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceCart}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCart
 * @generated
 */
@ProviderType
public class CommerceCartWrapper implements CommerceCart,
	ModelWrapper<CommerceCart> {
	public CommerceCartWrapper(CommerceCart commerceCart) {
		_commerceCart = commerceCart;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceCart.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceCart.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceCartId", getCommerceCartId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceCartId = (Long)attributes.get("commerceCartId");

		if (commerceCartId != null) {
			setCommerceCartId(commerceCartId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceCartWrapper((CommerceCart)_commerceCart.clone());
	}

	@Override
	public int compareTo(CommerceCart commerceCart) {
		return _commerceCart.compareTo(commerceCart);
	}

	/**
	* Returns the commerce cart ID of this commerce cart.
	*
	* @return the commerce cart ID of this commerce cart
	*/
	@Override
	public long getCommerceCartId() {
		return _commerceCart.getCommerceCartId();
	}

	@Override
	public java.util.List<CommerceCartItem> getCommerceCartItems() {
		return _commerceCart.getCommerceCartItems();
	}

	/**
	* Returns the company ID of this commerce cart.
	*
	* @return the company ID of this commerce cart
	*/
	@Override
	public long getCompanyId() {
		return _commerceCart.getCompanyId();
	}

	/**
	* Returns the create date of this commerce cart.
	*
	* @return the create date of this commerce cart
	*/
	@Override
	public Date getCreateDate() {
		return _commerceCart.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceCart.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce cart.
	*
	* @return the group ID of this commerce cart
	*/
	@Override
	public long getGroupId() {
		return _commerceCart.getGroupId();
	}

	/**
	* Returns the modified date of this commerce cart.
	*
	* @return the modified date of this commerce cart
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceCart.getModifiedDate();
	}

	/**
	* Returns the name of this commerce cart.
	*
	* @return the name of this commerce cart
	*/
	@Override
	public java.lang.String getName() {
		return _commerceCart.getName();
	}

	/**
	* Returns the primary key of this commerce cart.
	*
	* @return the primary key of this commerce cart
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceCart.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceCart.getPrimaryKeyObj();
	}

	/**
	* Returns the type of this commerce cart.
	*
	* @return the type of this commerce cart
	*/
	@Override
	public int getType() {
		return _commerceCart.getType();
	}

	/**
	* Returns the user ID of this commerce cart.
	*
	* @return the user ID of this commerce cart
	*/
	@Override
	public long getUserId() {
		return _commerceCart.getUserId();
	}

	/**
	* Returns the user name of this commerce cart.
	*
	* @return the user name of this commerce cart
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceCart.getUserName();
	}

	/**
	* Returns the user uuid of this commerce cart.
	*
	* @return the user uuid of this commerce cart
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceCart.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce cart.
	*
	* @return the uuid of this commerce cart
	*/
	@Override
	public java.lang.String getUuid() {
		return _commerceCart.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceCart.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceCart.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceCart.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceCart.isNew();
	}

	@Override
	public void persist() {
		_commerceCart.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceCart.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce cart ID of this commerce cart.
	*
	* @param commerceCartId the commerce cart ID of this commerce cart
	*/
	@Override
	public void setCommerceCartId(long commerceCartId) {
		_commerceCart.setCommerceCartId(commerceCartId);
	}

	/**
	* Sets the company ID of this commerce cart.
	*
	* @param companyId the company ID of this commerce cart
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceCart.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce cart.
	*
	* @param createDate the create date of this commerce cart
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceCart.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceCart.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceCart.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceCart.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce cart.
	*
	* @param groupId the group ID of this commerce cart
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceCart.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce cart.
	*
	* @param modifiedDate the modified date of this commerce cart
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceCart.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce cart.
	*
	* @param name the name of this commerce cart
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceCart.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceCart.setNew(n);
	}

	/**
	* Sets the primary key of this commerce cart.
	*
	* @param primaryKey the primary key of this commerce cart
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceCart.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceCart.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type of this commerce cart.
	*
	* @param type the type of this commerce cart
	*/
	@Override
	public void setType(int type) {
		_commerceCart.setType(type);
	}

	/**
	* Sets the user ID of this commerce cart.
	*
	* @param userId the user ID of this commerce cart
	*/
	@Override
	public void setUserId(long userId) {
		_commerceCart.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce cart.
	*
	* @param userName the user name of this commerce cart
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceCart.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce cart.
	*
	* @param userUuid the user uuid of this commerce cart
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceCart.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce cart.
	*
	* @param uuid the uuid of this commerce cart
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commerceCart.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceCart> toCacheModel() {
		return _commerceCart.toCacheModel();
	}

	@Override
	public CommerceCart toEscapedModel() {
		return new CommerceCartWrapper(_commerceCart.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceCart.toString();
	}

	@Override
	public CommerceCart toUnescapedModel() {
		return new CommerceCartWrapper(_commerceCart.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceCart.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCartWrapper)) {
			return false;
		}

		CommerceCartWrapper commerceCartWrapper = (CommerceCartWrapper)obj;

		if (Objects.equals(_commerceCart, commerceCartWrapper._commerceCart)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceCart.getStagedModelType();
	}

	@Override
	public CommerceCart getWrappedModel() {
		return _commerceCart;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceCart.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceCart.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceCart.resetOriginalValues();
	}

	private final CommerceCart _commerceCart;
}