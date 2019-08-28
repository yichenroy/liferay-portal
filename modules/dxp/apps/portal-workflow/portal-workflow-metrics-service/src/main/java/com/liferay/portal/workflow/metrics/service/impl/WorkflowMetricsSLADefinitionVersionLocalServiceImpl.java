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

package com.liferay.portal.workflow.metrics.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.workflow.metrics.exception.NoSuchSLADefinitionVersionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.service.base.WorkflowMetricsSLADefinitionVersionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the workflow metrics sla definition version local
 * service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * <code>com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionVersionLocalService</code>
 * interface.  <p> This is a local service. Methods of this service will not
 * have security checks based on the propagated JAAS credentials because this
 * service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    WorkflowMetricsSLADefinitionVersionLocalServiceBaseImpl
 */
public class WorkflowMetricsSLADefinitionVersionLocalServiceImpl
	extends WorkflowMetricsSLADefinitionVersionLocalServiceBaseImpl {

	@Override
	public WorkflowMetricsSLADefinitionVersion
			getWorkflowMetricsSLADefinitionVersion(
				long workflowMetricsSLADefinitionId, String version)
		throws NoSuchSLADefinitionVersionException {

		return workflowMetricsSLADefinitionVersionPersistence.findByWMSLAD_V(
			workflowMetricsSLADefinitionId, version);
	}

	@Override
	public List<WorkflowMetricsSLADefinitionVersion>
		getWorkflowMetricsSLADefinitionVersions(
			long workflowMetricsSLADefinitionId) {

		return workflowMetricsSLADefinitionVersionPersistence.
			findByWorkflowMetricsSLADefinitionId(
				workflowMetricsSLADefinitionId);
	}

	@Override
	public List<WorkflowMetricsSLADefinitionVersion>
		getWorkflowMetricsSLADefinitionVersions(
			long companyId, Date createDate, int status) {

		return workflowMetricsSLADefinitionVersionFinder.findByC_WMSLAD_V(
			companyId, createDate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

}