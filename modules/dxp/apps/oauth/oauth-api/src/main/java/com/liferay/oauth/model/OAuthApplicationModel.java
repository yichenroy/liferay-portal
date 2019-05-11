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

package com.liferay.oauth.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the OAuthApplication service. Represents a row in the &quot;OAuth_OAuthApplication&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.oauth.model.impl.OAuthApplicationModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.oauth.model.impl.OAuthApplicationImpl</code>.
 * </p>
 *
 * @author Ivica Cardic
 * @see OAuthApplication
 * @generated
 */
@ProviderType
public interface OAuthApplicationModel
	extends AuditedModel, BaseModel<OAuthApplication>, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a o auth application model instance should use the {@link OAuthApplication} interface instead.
	 */

	/**
	 * Returns the primary key of this o auth application.
	 *
	 * @return the primary key of this o auth application
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this o auth application.
	 *
	 * @param primaryKey the primary key of this o auth application
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the o auth application ID of this o auth application.
	 *
	 * @return the o auth application ID of this o auth application
	 */
	public long getOAuthApplicationId();

	/**
	 * Sets the o auth application ID of this o auth application.
	 *
	 * @param oAuthApplicationId the o auth application ID of this o auth application
	 */
	public void setOAuthApplicationId(long oAuthApplicationId);

	/**
	 * Returns the company ID of this o auth application.
	 *
	 * @return the company ID of this o auth application
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this o auth application.
	 *
	 * @param companyId the company ID of this o auth application
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this o auth application.
	 *
	 * @return the user ID of this o auth application
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this o auth application.
	 *
	 * @param userId the user ID of this o auth application
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this o auth application.
	 *
	 * @return the user uuid of this o auth application
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this o auth application.
	 *
	 * @param userUuid the user uuid of this o auth application
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this o auth application.
	 *
	 * @return the user name of this o auth application
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this o auth application.
	 *
	 * @param userName the user name of this o auth application
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this o auth application.
	 *
	 * @return the create date of this o auth application
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this o auth application.
	 *
	 * @param createDate the create date of this o auth application
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this o auth application.
	 *
	 * @return the modified date of this o auth application
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this o auth application.
	 *
	 * @param modifiedDate the modified date of this o auth application
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the name of this o auth application.
	 *
	 * @return the name of this o auth application
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this o auth application.
	 *
	 * @param name the name of this o auth application
	 */
	public void setName(String name);

	/**
	 * Returns the description of this o auth application.
	 *
	 * @return the description of this o auth application
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this o auth application.
	 *
	 * @param description the description of this o auth application
	 */
	public void setDescription(String description);

	/**
	 * Returns the consumer key of this o auth application.
	 *
	 * @return the consumer key of this o auth application
	 */
	@AutoEscape
	public String getConsumerKey();

	/**
	 * Sets the consumer key of this o auth application.
	 *
	 * @param consumerKey the consumer key of this o auth application
	 */
	public void setConsumerKey(String consumerKey);

	/**
	 * Returns the consumer secret of this o auth application.
	 *
	 * @return the consumer secret of this o auth application
	 */
	@AutoEscape
	public String getConsumerSecret();

	/**
	 * Sets the consumer secret of this o auth application.
	 *
	 * @param consumerSecret the consumer secret of this o auth application
	 */
	public void setConsumerSecret(String consumerSecret);

	/**
	 * Returns the access level of this o auth application.
	 *
	 * @return the access level of this o auth application
	 */
	public int getAccessLevel();

	/**
	 * Sets the access level of this o auth application.
	 *
	 * @param accessLevel the access level of this o auth application
	 */
	public void setAccessLevel(int accessLevel);

	/**
	 * Returns the logo ID of this o auth application.
	 *
	 * @return the logo ID of this o auth application
	 */
	public long getLogoId();

	/**
	 * Sets the logo ID of this o auth application.
	 *
	 * @param logoId the logo ID of this o auth application
	 */
	public void setLogoId(long logoId);

	/**
	 * Returns the shareable access token of this o auth application.
	 *
	 * @return the shareable access token of this o auth application
	 */
	public boolean getShareableAccessToken();

	/**
	 * Returns <code>true</code> if this o auth application is shareable access token.
	 *
	 * @return <code>true</code> if this o auth application is shareable access token; <code>false</code> otherwise
	 */
	public boolean isShareableAccessToken();

	/**
	 * Sets whether this o auth application is shareable access token.
	 *
	 * @param shareableAccessToken the shareable access token of this o auth application
	 */
	public void setShareableAccessToken(boolean shareableAccessToken);

	/**
	 * Returns the callback uri of this o auth application.
	 *
	 * @return the callback uri of this o auth application
	 */
	@AutoEscape
	public String getCallbackURI();

	/**
	 * Sets the callback uri of this o auth application.
	 *
	 * @param callbackURI the callback uri of this o auth application
	 */
	public void setCallbackURI(String callbackURI);

	/**
	 * Returns the website url of this o auth application.
	 *
	 * @return the website url of this o auth application
	 */
	@AutoEscape
	public String getWebsiteURL();

	/**
	 * Sets the website url of this o auth application.
	 *
	 * @param websiteURL the website url of this o auth application
	 */
	public void setWebsiteURL(String websiteURL);

}