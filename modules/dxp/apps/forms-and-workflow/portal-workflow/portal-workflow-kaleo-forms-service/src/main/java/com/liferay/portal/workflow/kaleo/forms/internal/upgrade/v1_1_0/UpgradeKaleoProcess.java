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

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_1_0;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalService;
import com.liferay.dynamic.data.mapping.service.permission.DDMStructurePermission;
import com.liferay.dynamic.data.mapping.service.permission.DDMTemplatePermission;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.List;

/**
 * @author Inácio Nery
 */
public class UpgradeKaleoProcess extends UpgradeProcess {

	public UpgradeKaleoProcess(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLocalService ddmTemplateLocalService,
		DDMTemplateVersionLocalService ddmTemplateVersionLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourceActions resourceActions,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmTemplateLocalService = ddmTemplateLocalService;
		_ddmTemplateVersionLocalService = ddmTemplateVersionLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourceActions = resourceActions;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		initKaleoFormsDDMCompositeModelsResourceActions();

		updateKaleoProcess();
		updateKaleoProcessLink();
	}

	protected String getDDMStructureModelResourceName(DDMStructure ddmStructure)
		throws PortalException {

		return DDMStructurePermission.getStructureModelResourceName(
			ddmStructure.getClassNameId());
	}

	protected String getDDMTemplateModelResourceName(DDMTemplate ddmTemplate)
		throws PortalException {

		return DDMTemplatePermission.getTemplateModelResourceName(
			ddmTemplate.getResourceClassNameId());
	}

	protected Long getNewDDMStructureId(long oldDDMStructureId)
		throws PortalException {

		Long newDDMStructureId = _ddmStructureMap.get(oldDDMStructureId);

		if (newDDMStructureId != null) {
			return newDDMStructureId;
		}

		DDMStructure oldDDMStructure = _ddmStructureLocalService.getStructure(
			oldDDMStructureId);

		ServiceContext serviceContext = new ServiceContext();

		DDMStructureVersion ddmStructureVersion =
			oldDDMStructure.getStructureVersion();

		serviceContext.setAttribute("status", ddmStructureVersion.getStatus());

		ModelPermissions oldDDMStructureModelPermissions =
			getResourceModelPermissions(
				oldDDMStructure.getCompanyId(),
				getDDMStructureModelResourceName(oldDDMStructure),
				oldDDMStructureId);

		serviceContext.setModelPermissions(oldDDMStructureModelPermissions);

		DDMStructure newDDMStructure = _ddmStructureLocalService.addStructure(
			oldDDMStructure.getUserId(), oldDDMStructure.getGroupId(),
			oldDDMStructure.getParentStructureId(), _kaleoProcessClassNameId,
			oldDDMStructure.getStructureKey(), oldDDMStructure.getNameMap(),
			oldDDMStructure.getDescriptionMap(), oldDDMStructure.getDDMForm(),
			oldDDMStructure.getDDMFormLayout(),
			oldDDMStructure.getStorageType(), oldDDMStructure.getType(),
			serviceContext);

		newDDMStructureId = newDDMStructure.getStructureId();

		_ddmStructureMap.put(oldDDMStructureId, newDDMStructureId);

		return newDDMStructureId;
	}

	protected Long getNewDDMTemplateId(long oldDDMTemplateId)
		throws PortalException {

		Long newDDMTemplateId = _ddmTemplateMap.get(oldDDMTemplateId);

		if (newDDMTemplateId != null) {
			return newDDMTemplateId;
		}

		DDMTemplate oldDDMTemplate = _ddmTemplateLocalService.getTemplate(
			oldDDMTemplateId);

		ServiceContext serviceContext = new ServiceContext();

		DDMTemplateVersion ddmTemplateVersion =
			_ddmTemplateVersionLocalService.getTemplateVersion(
				oldDDMTemplate.getTemplateId(), oldDDMTemplate.getVersion());

		serviceContext.setAttribute("status", ddmTemplateVersion.getStatus());

		ModelPermissions modelPermissions = getResourceModelPermissions(
			oldDDMTemplate.getCompanyId(),
			getDDMTemplateModelResourceName(oldDDMTemplate), oldDDMTemplateId);

		serviceContext.setModelPermissions(modelPermissions);

		Long newDDMStructureId = getNewDDMStructureId(
			oldDDMTemplate.getClassPK());

		DDMTemplate newDDMTemplate = _ddmTemplateLocalService.addTemplate(
			oldDDMTemplate.getUserId(), oldDDMTemplate.getGroupId(),
			oldDDMTemplate.getClassNameId(), newDDMStructureId,
			_kaleoProcessClassNameId, oldDDMTemplate.getNameMap(),
			oldDDMTemplate.getDescriptionMap(), oldDDMTemplate.getType(),
			oldDDMTemplate.getMode(), oldDDMTemplate.getLanguage(),
			oldDDMTemplate.getScript(), serviceContext);

		newDDMTemplateId = newDDMTemplate.getTemplateId();

		_ddmTemplateMap.put(oldDDMTemplateId, newDDMTemplateId);

		return newDDMTemplateId;
	}

	protected ModelPermissions getResourceModelPermissions(
			long companyId, String resourceName, long primKey)
		throws PortalException {

		ModelPermissions modelPermissions = new ModelPermissions();

		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(resourceName);

		for (ResourceAction resourceAction : resourceActions) {
			List<Role> roles = _resourcePermissionLocalService.getRoles(
				companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(primKey), resourceAction.getActionId());

			for (Role role : roles) {
				modelPermissions.addRolePermissions(
					role.getName(), resourceAction.getActionId());
			}
		}

		return modelPermissions;
	}

	protected void initKaleoFormsDDMCompositeModelsResourceActions()
		throws Exception {

		_resourceActions.read(
			null, UpgradeKaleoProcess.class.getClassLoader(),
			"/META-INF/resource-actions/default.xml");

		List<String> modelNames = _resourceActions.getPortletModelResources(
			KaleoFormsPortletKeys.KALEO_FORMS_ADMIN);

		for (String modelName : modelNames) {
			List<String> modelActions =
				_resourceActions.getModelResourceActions(modelName);

			_resourceActionLocalService.checkResourceActions(
				modelName, modelActions);
		}
	}

	protected void updateDDLRecordSet(
			long ddlRecordSetId, Long newDDMStructureId)
		throws PortalException {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			ddlRecordSetId);

		ddlRecordSet.setDDMStructureId(newDDMStructureId);

		_ddlRecordSetLocalService.updateDDLRecordSet(ddlRecordSet);
	}

	protected void updateKaleoProcess() throws Exception {
		StringBundler sb = new StringBundler(8);

		sb.append("select KaleoProcess.kaleoProcessId, ");
		sb.append("KaleoProcess.DDLRecordSetId, KaleoProcess.DDMTemplateId, ");
		sb.append("DDLRecordSet.DDMStructureId FROM KaleoProcess join ");
		sb.append("DDLRecordSet on DDLRecordSet.recordSetId = ");
		sb.append("KaleoProcess.DDLRecordSetId join DDMStructure on ");
		sb.append("DDMStructure.structureId = DDLRecordSet.DDMStructureId ");
		sb.append("where DDMStructure.classNameId <> ");
		sb.append(_kaleoProcessClassNameId);

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update KaleoProcess set DDMTemplateId = ? where " +
						"kaleoProcessId = ?")) {

			while (rs.next()) {
				long kaleoProcessId = rs.getLong("kaleoProcessId");
				long ddlRecordSetId = rs.getLong("DDLRecordSetId");
				long ddmTemplateId = rs.getLong("DDMTemplateId");
				long ddmStructureId = rs.getLong("DDMStructureId");

				Long newDDMStructureId = getNewDDMStructureId(ddmStructureId);

				updateDDLRecordSet(ddlRecordSetId, newDDMStructureId);

				Long newDDMTemplateId = getNewDDMTemplateId(ddmTemplateId);

				ps2.setLong(1, newDDMTemplateId);

				ps2.setLong(2, kaleoProcessId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void updateKaleoProcessLink() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select KaleoProcessLink.kaleoProcessLinkId, ");
		sb.append("KaleoProcessLink.DDMTemplateId FROM KaleoProcessLink join ");
		sb.append("DDMTemplate on DDMTemplate.templateId = KaleoProcessLink.");
		sb.append("DDMTemplateId where DDMTemplate.resourceClassNameId <> ");
		sb.append(_kaleoProcessClassNameId);

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update KaleoProcessLink set DDMTemplateId = ? where " +
						"kaleoProcessLinkId = ?")) {

			while (rs.next()) {
				long kaleoProcessLinkId = rs.getLong("kaleoProcessLinkId");
				long ddmTemplateId = rs.getLong("DDMTemplateId");

				Long newDDMTemplateId = getNewDDMTemplateId(ddmTemplateId);

				ps2.setLong(1, newDDMTemplateId);

				ps2.setLong(2, kaleoProcessLinkId);

				ps2.addBatch();
			}

			ps2.executeBatch();

		}
	}

	private static final long _kaleoProcessClassNameId =
		PortalUtil.getClassNameId(KaleoProcess.class);

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final HashMap<Long, Long> _ddmStructureMap = new HashMap<>();
	private final DDMTemplateLocalService _ddmTemplateLocalService;
	private final HashMap<Long, Long> _ddmTemplateMap = new HashMap<>();
	private final DDMTemplateVersionLocalService
		_ddmTemplateVersionLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceActions _resourceActions;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}