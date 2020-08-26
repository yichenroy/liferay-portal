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

package com.liferay.configuration.admin.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.internal.display.context.ConfigurationScopeDisplayContext;
import com.liferay.configuration.admin.web.internal.display.context.ConfigurationScopeDisplayContextFactory;
import com.liferay.configuration.admin.web.internal.exporter.ConfigurationExporter;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.configuration.admin.web.internal.util.AttributeDefinitionUtil;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelRetriever;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.util.PropsValues;

import java.io.FileInputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.cm.Configuration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.AttributeDefinition;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 * @author Eduardo García
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=export"
	},
	service = MVCResourceCommand.class
)
public class ExportConfigurationMVCResourceCommand
	implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		String pid = ParamUtil.getString(resourceRequest, "pid");
		String factoryPid = ParamUtil.getString(resourceRequest, "factoryPid");

		try {
			if (Validator.isNotNull(pid)) {
				exportPid(resourceRequest, resourceResponse);
			}
			else if (Validator.isNotNull(factoryPid)) {
				exportFactoryPid(resourceRequest, resourceResponse);
			}
			else {
				exportAll(resourceRequest, resourceResponse);
			}
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return false;
	}

	protected void exportAll(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		ConfigurationScopeDisplayContext configurationScopeDisplayContext =
			ConfigurationScopeDisplayContextFactory.create(resourceRequest);

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				themeDisplay.getLanguageId(),
				configurationScopeDisplayContext.getScope(),
				configurationScopeDisplayContext.getScopePK());

		for (ConfigurationModel configurationModel :
				configurationModels.values()) {

			if (configurationModel.isFactory()) {
				String curFactoryPid = configurationModel.getFactoryPid();

				List<ConfigurationModel> factoryInstances =
					_configurationModelRetriever.getFactoryInstances(
						configurationModel,
						configurationScopeDisplayContext.getScope(),
						configurationScopeDisplayContext.getScopePK());

				for (ConfigurationModel factoryInstance : factoryInstances) {
					String curPid = factoryInstance.getID();

					String curFileName = getFileName(curFactoryPid, curPid);

					zipWriter.addEntry(
						curFileName,
						ConfigurationExporter.getPropertiesAsBytes(
							getProperties(
								languageId, curFactoryPid, curPid,
								configurationScopeDisplayContext.getScope(),
								configurationScopeDisplayContext.
									getScopePK())));
				}
			}
			else if (configurationModel.hasConfiguration()) {
				String curPid = configurationModel.getID();

				String curFileName = getFileName(null, curPid);

				zipWriter.addEntry(
					curFileName,
					ConfigurationExporter.getPropertiesAsBytes(
						getProperties(
							languageId, curPid, curPid,
							configurationScopeDisplayContext.getScope(),
							configurationScopeDisplayContext.getScopePK())));
			}
		}

		String fileName = "liferay-system-settings.zip";

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileName,
			new FileInputStream(zipWriter.getFile()),
			ContentTypes.APPLICATION_ZIP);
	}

	protected void exportFactoryPid(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		String factoryPid = ParamUtil.getString(resourceRequest, "factoryPid");

		ConfigurationScopeDisplayContext configurationScopeDisplayContext =
			ConfigurationScopeDisplayContextFactory.create(resourceRequest);

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				themeDisplay.getLanguageId(),
				configurationScopeDisplayContext.getScope(),
				configurationScopeDisplayContext.getScopePK());

		ConfigurationModel factoryConfigurationModel = configurationModels.get(
			factoryPid);

		List<ConfigurationModel> factoryInstances =
			_configurationModelRetriever.getFactoryInstances(
				factoryConfigurationModel,
				configurationScopeDisplayContext.getScope(),
				configurationScopeDisplayContext.getScopePK());

		for (ConfigurationModel factoryInstance : factoryInstances) {
			String curPid = factoryInstance.getID();

			String curFileName = getFileName(factoryPid, curPid);

			zipWriter.addEntry(
				curFileName,
				ConfigurationExporter.getPropertiesAsBytes(
					getProperties(
						languageId, factoryPid, curPid,
						configurationScopeDisplayContext.getScope(),
						configurationScopeDisplayContext.getScopePK())));
		}

		String fileName =
			"liferay-system-settings-" +
				factoryConfigurationModel.getFactoryPid() + ".zip";

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileName,
			new FileInputStream(zipWriter.getFile()),
			ContentTypes.APPLICATION_ZIP);
	}

	protected void exportPid(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String factoryPid = ParamUtil.getString(resourceRequest, "factoryPid");
		String pid = ParamUtil.getString(resourceRequest, "pid");

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		String fileName = getFileName(factoryPid, pid);

		ConfigurationScopeDisplayContext configurationScopeDisplayContext =
			ConfigurationScopeDisplayContextFactory.create(resourceRequest);

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileName,
			ConfigurationExporter.getPropertiesAsBytes(
				getProperties(
					languageId, factoryPid, pid,
					configurationScopeDisplayContext.getScope(),
					configurationScopeDisplayContext.getScopePK())),
			ContentTypes.TEXT_XML_UTF8);
	}

	protected String getFileName(String factoryPid, String pid) {
		String fileName = pid;

		if (Validator.isNotNull(factoryPid) && !factoryPid.equals(pid)) {
			String factoryInstanceId = pid.substring(factoryPid.length() + 1);

			fileName = factoryPid + StringPool.DASH + factoryInstanceId;
		}

		return fileName + ".config";
	}

	protected Properties getProperties(
			String languageId, String factoryPid, String pid, Scope scope,
			Serializable scopePK)
		throws Exception {

		Properties properties = new Properties();

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				languageId, scope, scopePK);

		ConfigurationModel configurationModel = configurationModels.get(pid);

		if ((configurationModel == null) && Validator.isNotNull(factoryPid)) {
			configurationModel = configurationModels.get(factoryPid);
		}

		if (configurationModel == null) {
			return properties;
		}

		Configuration configuration =
			_configurationModelRetriever.getConfiguration(pid, scope, scopePK);

		if (configuration == null) {
			return properties;
		}

		ExtendedObjectClassDefinition extendedObjectClassDefinition =
			configurationModel.getExtendedObjectClassDefinition();

		ExtendedAttributeDefinition[] attributeDefinitions =
			extendedObjectClassDefinition.getAttributeDefinitions(
				ConfigurationModel.ALL);

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			if (!PropsValues.MODULE_FRAMEWORK_EXPORT_PASSWORD_ATTRIBUTES &&
				(attributeDefinition.getType() ==
					AttributeDefinition.PASSWORD)) {

				continue;
			}

			Object value = AttributeDefinitionUtil.getPropertyObject(
				attributeDefinition, configuration);

			if (value == null) {
				continue;
			}

			properties.put(attributeDefinition.getID(), value);
		}

		if (!Scope.SYSTEM.equals(scope)) {
			properties.put(scope.getPropertyKey(), scopePK);
		}

		return properties;
	}

	@Reference(target = "(filter.visibility=*)")
	private ConfigurationModelRetriever _configurationModelRetriever;

}