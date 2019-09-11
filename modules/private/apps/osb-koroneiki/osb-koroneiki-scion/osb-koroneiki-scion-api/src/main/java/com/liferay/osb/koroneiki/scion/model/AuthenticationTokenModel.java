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

package com.liferay.osb.koroneiki.scion.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the AuthenticationToken service. Represents a row in the &quot;Koroneiki_AuthenticationToken&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.osb.koroneiki.scion.model.impl.AuthenticationTokenModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.osb.koroneiki.scion.model.impl.AuthenticationTokenImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AuthenticationToken
 * @generated
 */
@ProviderType
public interface AuthenticationTokenModel
	extends BaseModel<AuthenticationToken>, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a authentication token model instance should use the {@link AuthenticationToken} interface instead.
	 */

	/**
	 * Returns the primary key of this authentication token.
	 *
	 * @return the primary key of this authentication token
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this authentication token.
	 *
	 * @param primaryKey the primary key of this authentication token
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the authentication token ID of this authentication token.
	 *
	 * @return the authentication token ID of this authentication token
	 */
	public long getAuthenticationTokenId();

	/**
	 * Sets the authentication token ID of this authentication token.
	 *
	 * @param authenticationTokenId the authentication token ID of this authentication token
	 */
	public void setAuthenticationTokenId(long authenticationTokenId);

	/**
	 * Returns the company ID of this authentication token.
	 *
	 * @return the company ID of this authentication token
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this authentication token.
	 *
	 * @param companyId the company ID of this authentication token
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this authentication token.
	 *
	 * @return the user ID of this authentication token
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this authentication token.
	 *
	 * @param userId the user ID of this authentication token
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this authentication token.
	 *
	 * @return the user uuid of this authentication token
	 */
	public String getUserUuid();

	/**
	 * Sets the user uuid of this authentication token.
	 *
	 * @param userUuid the user uuid of this authentication token
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the create date of this authentication token.
	 *
	 * @return the create date of this authentication token
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this authentication token.
	 *
	 * @param createDate the create date of this authentication token
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this authentication token.
	 *
	 * @return the modified date of this authentication token
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this authentication token.
	 *
	 * @param modifiedDate the modified date of this authentication token
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the service producer ID of this authentication token.
	 *
	 * @return the service producer ID of this authentication token
	 */
	public long getServiceProducerId();

	/**
	 * Sets the service producer ID of this authentication token.
	 *
	 * @param serviceProducerId the service producer ID of this authentication token
	 */
	public void setServiceProducerId(long serviceProducerId);

	/**
	 * Returns the name of this authentication token.
	 *
	 * @return the name of this authentication token
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this authentication token.
	 *
	 * @param name the name of this authentication token
	 */
	public void setName(String name);

	/**
	 * Returns the prefix of this authentication token.
	 *
	 * @return the prefix of this authentication token
	 */
	@AutoEscape
	public String getPrefix();

	/**
	 * Sets the prefix of this authentication token.
	 *
	 * @param prefix the prefix of this authentication token
	 */
	public void setPrefix(String prefix);

	/**
	 * Returns the digest of this authentication token.
	 *
	 * @return the digest of this authentication token
	 */
	@AutoEscape
	public String getDigest();

	/**
	 * Sets the digest of this authentication token.
	 *
	 * @param digest the digest of this authentication token
	 */
	public void setDigest(String digest);

	/**
	 * Returns the status of this authentication token.
	 *
	 * @return the status of this authentication token
	 */
	public int getStatus();

	/**
	 * Sets the status of this authentication token.
	 *
	 * @param status the status of this authentication token
	 */
	public void setStatus(int status);

}