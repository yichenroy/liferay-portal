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

package com.liferay.commerce.cart.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the CommerceCartItem service. Represents a row in the &quot;CommerceCartItem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.commerce.cart.model.impl.CommerceCartItemModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.commerce.cart.model.impl.CommerceCartItemImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartItem
 * @see com.liferay.commerce.cart.model.impl.CommerceCartItemImpl
 * @see com.liferay.commerce.cart.model.impl.CommerceCartItemModelImpl
 * @generated
 */
@ProviderType
public interface CommerceCartItemModel extends BaseModel<CommerceCartItem>,
	GroupedModel, ShardedModel, StagedAuditedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a commerce cart item model instance should use the {@link CommerceCartItem} interface instead.
	 */

	/**
	 * Returns the primary key of this commerce cart item.
	 *
	 * @return the primary key of this commerce cart item
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this commerce cart item.
	 *
	 * @param primaryKey the primary key of this commerce cart item
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this commerce cart item.
	 *
	 * @return the uuid of this commerce cart item
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this commerce cart item.
	 *
	 * @param uuid the uuid of this commerce cart item
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the commerce cart item ID of this commerce cart item.
	 *
	 * @return the commerce cart item ID of this commerce cart item
	 */
	public long getCommerceCartItemId();

	/**
	 * Sets the commerce cart item ID of this commerce cart item.
	 *
	 * @param CommerceCartItemId the commerce cart item ID of this commerce cart item
	 */
	public void setCommerceCartItemId(long CommerceCartItemId);

	/**
	 * Returns the group ID of this commerce cart item.
	 *
	 * @return the group ID of this commerce cart item
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this commerce cart item.
	 *
	 * @param groupId the group ID of this commerce cart item
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this commerce cart item.
	 *
	 * @return the company ID of this commerce cart item
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this commerce cart item.
	 *
	 * @param companyId the company ID of this commerce cart item
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this commerce cart item.
	 *
	 * @return the user ID of this commerce cart item
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this commerce cart item.
	 *
	 * @param userId the user ID of this commerce cart item
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this commerce cart item.
	 *
	 * @return the user uuid of this commerce cart item
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this commerce cart item.
	 *
	 * @param userUuid the user uuid of this commerce cart item
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this commerce cart item.
	 *
	 * @return the user name of this commerce cart item
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this commerce cart item.
	 *
	 * @param userName the user name of this commerce cart item
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this commerce cart item.
	 *
	 * @return the create date of this commerce cart item
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this commerce cart item.
	 *
	 * @param createDate the create date of this commerce cart item
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this commerce cart item.
	 *
	 * @return the modified date of this commerce cart item
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this commerce cart item.
	 *
	 * @param modifiedDate the modified date of this commerce cart item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the commerce cart ID of this commerce cart item.
	 *
	 * @return the commerce cart ID of this commerce cart item
	 */
	public long getCommerceCartId();

	/**
	 * Sets the commerce cart ID of this commerce cart item.
	 *
	 * @param CommerceCartId the commerce cart ID of this commerce cart item
	 */
	public void setCommerceCartId(long CommerceCartId);

	/**
	 * Returns the cp definition ID of this commerce cart item.
	 *
	 * @return the cp definition ID of this commerce cart item
	 */
	public long getCPDefinitionId();

	/**
	 * Sets the cp definition ID of this commerce cart item.
	 *
	 * @param CPDefinitionId the cp definition ID of this commerce cart item
	 */
	public void setCPDefinitionId(long CPDefinitionId);

	/**
	 * Returns the cp instance ID of this commerce cart item.
	 *
	 * @return the cp instance ID of this commerce cart item
	 */
	public long getCPInstanceId();

	/**
	 * Sets the cp instance ID of this commerce cart item.
	 *
	 * @param CPInstanceId the cp instance ID of this commerce cart item
	 */
	public void setCPInstanceId(long CPInstanceId);

	/**
	 * Returns the quantity of this commerce cart item.
	 *
	 * @return the quantity of this commerce cart item
	 */
	public int getQuantity();

	/**
	 * Sets the quantity of this commerce cart item.
	 *
	 * @param quantity the quantity of this commerce cart item
	 */
	public void setQuantity(int quantity);

	/**
	 * Returns the json of this commerce cart item.
	 *
	 * @return the json of this commerce cart item
	 */
	@AutoEscape
	public String getJson();

	/**
	 * Sets the json of this commerce cart item.
	 *
	 * @param json the json of this commerce cart item
	 */
	public void setJson(String json);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(CommerceCartItem commerceCartItem);

	@Override
	public int hashCode();

	@Override
	public CacheModel<CommerceCartItem> toCacheModel();

	@Override
	public CommerceCartItem toEscapedModel();

	@Override
	public CommerceCartItem toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}