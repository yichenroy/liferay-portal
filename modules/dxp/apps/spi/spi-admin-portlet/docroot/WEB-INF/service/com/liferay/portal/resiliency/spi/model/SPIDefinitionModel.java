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

package com.liferay.portal.resiliency.spi.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the SPIDefinition service. Represents a row in the &quot;SPIDefinition&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionImpl</code>.
 * </p>
 *
 * @author Michael C. Han
 * @see SPIDefinition
 * @generated
 */
@ProviderType
public interface SPIDefinitionModel
	extends AuditedModel, BaseModel<SPIDefinition>, ShardedModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a spi definition model instance should use the {@link SPIDefinition} interface instead.
	 */

	/**
	 * Returns the primary key of this spi definition.
	 *
	 * @return the primary key of this spi definition
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this spi definition.
	 *
	 * @param primaryKey the primary key of this spi definition
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the spi definition ID of this spi definition.
	 *
	 * @return the spi definition ID of this spi definition
	 */
	public long getSpiDefinitionId();

	/**
	 * Sets the spi definition ID of this spi definition.
	 *
	 * @param spiDefinitionId the spi definition ID of this spi definition
	 */
	public void setSpiDefinitionId(long spiDefinitionId);

	/**
	 * Returns the company ID of this spi definition.
	 *
	 * @return the company ID of this spi definition
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this spi definition.
	 *
	 * @param companyId the company ID of this spi definition
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this spi definition.
	 *
	 * @return the user ID of this spi definition
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this spi definition.
	 *
	 * @param userId the user ID of this spi definition
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this spi definition.
	 *
	 * @return the user uuid of this spi definition
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this spi definition.
	 *
	 * @param userUuid the user uuid of this spi definition
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this spi definition.
	 *
	 * @return the user name of this spi definition
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this spi definition.
	 *
	 * @param userName the user name of this spi definition
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this spi definition.
	 *
	 * @return the create date of this spi definition
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this spi definition.
	 *
	 * @param createDate the create date of this spi definition
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this spi definition.
	 *
	 * @return the modified date of this spi definition
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this spi definition.
	 *
	 * @param modifiedDate the modified date of this spi definition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the name of this spi definition.
	 *
	 * @return the name of this spi definition
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this spi definition.
	 *
	 * @param name the name of this spi definition
	 */
	public void setName(String name);

	/**
	 * Returns the connector address of this spi definition.
	 *
	 * @return the connector address of this spi definition
	 */
	@AutoEscape
	public String getConnectorAddress();

	/**
	 * Sets the connector address of this spi definition.
	 *
	 * @param connectorAddress the connector address of this spi definition
	 */
	public void setConnectorAddress(String connectorAddress);

	/**
	 * Returns the connector port of this spi definition.
	 *
	 * @return the connector port of this spi definition
	 */
	public int getConnectorPort();

	/**
	 * Sets the connector port of this spi definition.
	 *
	 * @param connectorPort the connector port of this spi definition
	 */
	public void setConnectorPort(int connectorPort);

	/**
	 * Returns the description of this spi definition.
	 *
	 * @return the description of this spi definition
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this spi definition.
	 *
	 * @param description the description of this spi definition
	 */
	public void setDescription(String description);

	/**
	 * Returns the jvm arguments of this spi definition.
	 *
	 * @return the jvm arguments of this spi definition
	 */
	@AutoEscape
	public String getJvmArguments();

	/**
	 * Sets the jvm arguments of this spi definition.
	 *
	 * @param jvmArguments the jvm arguments of this spi definition
	 */
	public void setJvmArguments(String jvmArguments);

	/**
	 * Returns the portlet IDs of this spi definition.
	 *
	 * @return the portlet IDs of this spi definition
	 */
	@AutoEscape
	public String getPortletIds();

	/**
	 * Sets the portlet IDs of this spi definition.
	 *
	 * @param portletIds the portlet IDs of this spi definition
	 */
	public void setPortletIds(String portletIds);

	/**
	 * Returns the servlet context names of this spi definition.
	 *
	 * @return the servlet context names of this spi definition
	 */
	@AutoEscape
	public String getServletContextNames();

	/**
	 * Sets the servlet context names of this spi definition.
	 *
	 * @param servletContextNames the servlet context names of this spi definition
	 */
	public void setServletContextNames(String servletContextNames);

	/**
	 * Returns the type settings of this spi definition.
	 *
	 * @return the type settings of this spi definition
	 */
	@AutoEscape
	public String getTypeSettings();

	/**
	 * Sets the type settings of this spi definition.
	 *
	 * @param typeSettings the type settings of this spi definition
	 */
	public void setTypeSettings(String typeSettings);

	/**
	 * Returns the status of this spi definition.
	 *
	 * @return the status of this spi definition
	 */
	public int getStatus();

	/**
	 * Sets the status of this spi definition.
	 *
	 * @param status the status of this spi definition
	 */
	public void setStatus(int status);

	/**
	 * Returns the status message of this spi definition.
	 *
	 * @return the status message of this spi definition
	 */
	@AutoEscape
	public String getStatusMessage();

	/**
	 * Sets the status message of this spi definition.
	 *
	 * @param statusMessage the status message of this spi definition
	 */
	public void setStatusMessage(String statusMessage);

}