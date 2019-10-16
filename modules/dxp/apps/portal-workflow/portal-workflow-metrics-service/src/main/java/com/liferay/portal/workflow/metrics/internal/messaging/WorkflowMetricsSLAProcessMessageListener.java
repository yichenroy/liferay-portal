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

package com.liferay.portal.workflow.metrics.internal.messaging;

import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.metrics.internal.background.task.WorkflowMetricsSLAProcessBackgroundTaskExecutor;
import com.liferay.portal.workflow.metrics.internal.configuration.WorkflowMetricsConfiguration;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	configurationPid = "com.liferay.portal.workflow.metrics.configuration.WorkflowMetricsConfiguration",
	immediate = true, service = WorkflowMetricsSLAProcessMessageListener.class
)
public class WorkflowMetricsSLAProcessMessageListener
	extends BaseMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_workflowMetricsConfiguration = ConfigurableUtil.createConfigurable(
			WorkflowMetricsConfiguration.class, properties);

		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			_workflowMetricsConfiguration.checkSLAJobInterval(),
			TimeUnit.MINUTE);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_workflowMetricsSLADefinitionLocalService.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(WorkflowMetricsSLADefinition workflowMetricsSLADefinition) -> {
				Map<String, Serializable> taskContextMap = new HashMap<>();

				taskContextMap.put(
					BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS, true);
				taskContextMap.put(
					"workflowMetricsSLADefinitionId",
					workflowMetricsSLADefinition.getPrimaryKey());

				_backgroundTaskLocalService.addBackgroundTask(
					workflowMetricsSLADefinition.getUserId(),
					workflowMetricsSLADefinition.getGroupId(),
					_getBackgroundTaskName(workflowMetricsSLADefinition),
					WorkflowMetricsSLAProcessBackgroundTaskExecutor.class.
						getName(),
					taskContextMap, new ServiceContext());
			});
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private String _getBackgroundTaskName(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		return _BACKGROUND_TASK_NAME_PREFIX +
			workflowMetricsSLADefinition.getProcessId() +
				workflowMetricsSLADefinition.getPrimaryKey();
	}

	private static final String _BACKGROUND_TASK_NAME_PREFIX =
		"WorkflowMetricsSLAProcessorBackgroundTaskExecutor-";

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

	private volatile WorkflowMetricsConfiguration _workflowMetricsConfiguration;

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}