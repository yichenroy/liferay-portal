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

package com.liferay.portal.workflow.metrics.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;

import java.util.Date;

/**
 * The base model interface for the WorkflowMetricsSLACalendar service. Represents a row in the &quot;WorkflowMetricsSLACalendar&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLACalendarModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLACalendarImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLACalendar
 * @generated
 */
@ProviderType
public interface WorkflowMetricsSLACalendarModel extends BaseModel<WorkflowMetricsSLACalendar>,
	GroupedModel, MVCCModel, ShardedModel, StagedAuditedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a workflow metrics sla calendar model instance should use the {@link WorkflowMetricsSLACalendar} interface instead.
	 */

	/**
	 * Returns the primary key of this workflow metrics sla calendar.
	 *
	 * @return the primary key of this workflow metrics sla calendar
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this workflow metrics sla calendar.
	 *
	 * @param primaryKey the primary key of this workflow metrics sla calendar
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this workflow metrics sla calendar.
	 *
	 * @return the mvcc version of this workflow metrics sla calendar
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this workflow metrics sla calendar.
	 *
	 * @param mvccVersion the mvcc version of this workflow metrics sla calendar
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this workflow metrics sla calendar.
	 *
	 * @return the uuid of this workflow metrics sla calendar
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this workflow metrics sla calendar.
	 *
	 * @param uuid the uuid of this workflow metrics sla calendar
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the workflow metrics sla calendar ID of this workflow metrics sla calendar.
	 *
	 * @return the workflow metrics sla calendar ID of this workflow metrics sla calendar
	 */
	public long getWorkflowMetricsSLACalendarId();

	/**
	 * Sets the workflow metrics sla calendar ID of this workflow metrics sla calendar.
	 *
	 * @param workflowMetricsSLACalendarId the workflow metrics sla calendar ID of this workflow metrics sla calendar
	 */
	public void setWorkflowMetricsSLACalendarId(
		long workflowMetricsSLACalendarId);

	/**
	 * Returns the group ID of this workflow metrics sla calendar.
	 *
	 * @return the group ID of this workflow metrics sla calendar
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this workflow metrics sla calendar.
	 *
	 * @param groupId the group ID of this workflow metrics sla calendar
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this workflow metrics sla calendar.
	 *
	 * @return the company ID of this workflow metrics sla calendar
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this workflow metrics sla calendar.
	 *
	 * @param companyId the company ID of this workflow metrics sla calendar
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this workflow metrics sla calendar.
	 *
	 * @return the user ID of this workflow metrics sla calendar
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this workflow metrics sla calendar.
	 *
	 * @param userId the user ID of this workflow metrics sla calendar
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this workflow metrics sla calendar.
	 *
	 * @return the user uuid of this workflow metrics sla calendar
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this workflow metrics sla calendar.
	 *
	 * @param userUuid the user uuid of this workflow metrics sla calendar
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this workflow metrics sla calendar.
	 *
	 * @return the user name of this workflow metrics sla calendar
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this workflow metrics sla calendar.
	 *
	 * @param userName the user name of this workflow metrics sla calendar
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this workflow metrics sla calendar.
	 *
	 * @return the create date of this workflow metrics sla calendar
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this workflow metrics sla calendar.
	 *
	 * @param createDate the create date of this workflow metrics sla calendar
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this workflow metrics sla calendar.
	 *
	 * @return the modified date of this workflow metrics sla calendar
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this workflow metrics sla calendar.
	 *
	 * @param modifiedDate the modified date of this workflow metrics sla calendar
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);
}