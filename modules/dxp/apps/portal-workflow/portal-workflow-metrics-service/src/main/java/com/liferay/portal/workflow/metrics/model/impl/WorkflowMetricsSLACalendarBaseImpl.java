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

package com.liferay.portal.workflow.metrics.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLACalendarLocalServiceUtil;

/**
 * The extended model base implementation for the WorkflowMetricsSLACalendar service. Represents a row in the &quot;WorkflowMetricsSLACalendar&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link WorkflowMetricsSLACalendarImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLACalendarImpl
 * @see WorkflowMetricsSLACalendar
 * @generated
 */
@ProviderType
public abstract class WorkflowMetricsSLACalendarBaseImpl
	extends WorkflowMetricsSLACalendarModelImpl
	implements WorkflowMetricsSLACalendar {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a workflow metrics sla calendar model instance should use the <code>WorkflowMetricsSLACalendar</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			WorkflowMetricsSLACalendarLocalServiceUtil.
				addWorkflowMetricsSLACalendar(this);
		}
		else {
			WorkflowMetricsSLACalendarLocalServiceUtil.
				updateWorkflowMetricsSLACalendar(this);
		}
	}

}