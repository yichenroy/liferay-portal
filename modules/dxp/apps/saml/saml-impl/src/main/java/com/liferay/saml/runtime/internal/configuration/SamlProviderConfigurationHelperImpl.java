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

package com.liferay.saml.runtime.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import java.io.IOException;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	property = Constants.SERVICE_PID + "=" + SamlProviderConfigurationHelperImpl.FACTORY_PID,
	service = {
		ManagedServiceFactory.class, SamlProviderConfigurationHelper.class
	}
)
public class SamlProviderConfigurationHelperImpl
	implements ManagedServiceFactory, SamlProviderConfigurationHelper {

	@Override
	public void deleted(String pid) {
		ConfigurationHolder configurationHolder =
			_configurationHolderByPid.remove(pid);

		if (configurationHolder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to delete missing configuration " + pid);
			}

			return;
		}

		SamlProviderConfiguration samlProviderConfiguration =
			configurationHolder.getSamlProviderConfiguration();

		_configurationHolderByCompanyId.remove(
			samlProviderConfiguration.companyId());
	}

	@Override
	public String getName() {
		return "SAML Provider Configuration Factory";
	}

	@Override
	public SamlProviderConfiguration getSamlProviderConfiguration() {
		ConfigurationHolder configurationHolder =
			_configurationHolderByCompanyId.get(
				CompanyThreadLocal.getCompanyId());

		if (configurationHolder == null) {
			configurationHolder = _configurationHolderByCompanyId.get(
				CompanyConstants.SYSTEM);
		}

		if (configurationHolder != null) {
			return configurationHolder.getSamlProviderConfiguration();
		}

		return _defaultSamlProviderConfiguration;
	}

	@Override
	public boolean isEnabled() {
		SamlProviderConfiguration samlProviderConfiguration =
			getSamlProviderConfiguration();

		return samlProviderConfiguration.enabled();
	}

	@Override
	public boolean isLDAPImportEnabled() {
		SamlProviderConfiguration samlProviderConfiguration =
			getSamlProviderConfiguration();

		return samlProviderConfiguration.ldapImportEnabled();
	}

	@Override
	public boolean isRoleIdp() {
		SamlProviderConfiguration samlProviderConfiguration =
			getSamlProviderConfiguration();

		String role = samlProviderConfiguration.role();

		if (Validator.isNotNull(role) && role.equals("idp")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleSp() {
		SamlProviderConfiguration samlProviderConfiguration =
			getSamlProviderConfiguration();

		String role = samlProviderConfiguration.role();

		if (Validator.isNotNull(role) && role.equals("sp")) {
			return true;
		}

		return false;
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties)
		throws ConfigurationException {

		Long companyId = GetterUtil.getLong(properties.get("companyId"));

		SamlProviderConfiguration samlProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				SamlProviderConfiguration.class, properties);

		ConfigurationHolder configurationHolder = new ConfigurationHolder(
			samlProviderConfiguration, pid);

		_configurationHolderByCompanyId.put(companyId, configurationHolder);
		_configurationHolderByPid.put(pid, configurationHolder);
	}

	@Override
	public void updateProperties(UnicodeProperties properties)
		throws Exception {

		long companyId = CompanyThreadLocal.getCompanyId();

		ConfigurationHolder configurationHolder =
			_configurationHolderByCompanyId.get(companyId);

		Configuration configuration = null;
		Dictionary<String, Object> configurationProperties = null;

		if (configurationHolder == null) {
			configuration = _configurationAdmin.createFactoryConfiguration(
				FACTORY_PID, StringPool.QUESTION);

			configurationProperties = new HashMapDictionary<>();

			Dictionary<String, ?> systemProperties = getSystemProperties();

			if (systemProperties != null) {
				Enumeration<String> keys = systemProperties.keys();

				while (keys.hasMoreElements()) {
					String key = keys.nextElement();

					configurationProperties.put(key, systemProperties.get(key));
				}
			}
		}
		else {
			configuration = _configurationAdmin.getConfiguration(
				configurationHolder.getPid(), StringPool.QUESTION);

			configurationProperties = configuration.getProperties();
		}

		for (Map.Entry<String, String> mapEntry : properties.entrySet()) {
			configurationProperties.put(mapEntry.getKey(), mapEntry.getValue());
		}

		configurationProperties.put("companyId", companyId);

		configuration.update(configurationProperties);

		updated(configuration.getPid(), configuration.getProperties());
	}

	protected Dictionary<String, ?> getSystemProperties() throws IOException {
		ConfigurationHolder configurationHolder =
			_configurationHolderByCompanyId.get(CompanyConstants.SYSTEM);

		if (configurationHolder == null) {
			return null;
		}

		Configuration configuration = _configurationAdmin.getConfiguration(
			configurationHolder.getPid(), StringPool.QUESTION);

		return configuration.getProperties();
	}

	protected static final String FACTORY_PID =
		"com.liferay.saml.runtime.configuration.SamlProviderConfiguration";

	private static final Log _log = LogFactoryUtil.getLog(
		SamlProviderConfigurationHelperImpl.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private final Map<Long, ConfigurationHolder>
		_configurationHolderByCompanyId = new ConcurrentHashMap<>();
	private final Map<String, ConfigurationHolder> _configurationHolderByPid =
		new ConcurrentHashMap<>();
	private final SamlProviderConfiguration _defaultSamlProviderConfiguration =
		ConfigurableUtil.createConfigurable(
			SamlProviderConfiguration.class, Collections.emptyMap());

	private class ConfigurationHolder {

		public ConfigurationHolder(
			SamlProviderConfiguration samlProviderConfiguration, String pid) {

			_samlProviderConfiguration = samlProviderConfiguration;
			_pid = pid;
		}

		public String getPid() {
			return _pid;
		}

		public SamlProviderConfiguration getSamlProviderConfiguration() {
			return _samlProviderConfiguration;
		}

		private final String _pid;
		private final SamlProviderConfiguration _samlProviderConfiguration;

	}

}