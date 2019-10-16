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

package com.liferay.portal.workflow.metrics.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowMetricsSLADefinitionStopNodeKeysException
	extends PortalException {

	public WorkflowMetricsSLADefinitionStopNodeKeysException() {
	}

	public WorkflowMetricsSLADefinitionStopNodeKeysException(String msg) {
		super(msg);
	}

	public WorkflowMetricsSLADefinitionStopNodeKeysException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public WorkflowMetricsSLADefinitionStopNodeKeysException(Throwable cause) {
		super(cause);
	}

}