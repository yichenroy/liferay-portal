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

package com.liferay.saml.runtime.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.internal.constants.LegacySamlPropsKeys;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * @author Stian Sigvartsen
 * @author Tomas Polesovsky
 */
public class UpgradeSamlProviderConfigurationPreferences
	extends BaseUpgradeSaml {

	public UpgradeSamlProviderConfigurationPreferences(
		CompanyLocalService companyLocalService, PrefsProps prefsProps,
		Props props,
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		_companyLocalService = companyLocalService;
		_prefsProps = prefsProps;
		_props = props;
		_samlProviderConfigurationHelper = samlProviderConfigurationHelper;
	}

	public Set<String> migrateSAMLProviderConfigurationPreferences(
			long companyId)
		throws Exception {

		String prefsPropsFilterString = null;
		Filter propsFilter = null;

		PortletPreferences portletPreferences = _prefsProps.getPreferences(
			companyId, true);

		String entityId = portletPreferences.getValue(
			LegacySamlPropsKeys.SAML_ENTITY_ID, null);

		if (entityId == null) {
			entityId = _props.get(LegacySamlPropsKeys.SAML_ENTITY_ID);
		}

		if (Validator.isNotNull(entityId)) {
			prefsPropsFilterString = "[" + entityId + "]";
			propsFilter = new Filter(entityId);
		}

		Set<String> migratedPrefsPropsKeys = new HashSet<>();
		UnicodeProperties properties = new UnicodeProperties();

		for (String key : LegacySamlPropsKeys.SAML_KEYS_PREFS_PROPS) {
			if (ArrayUtil.contains(
					LegacySamlPropsKeys.SAML_KEYS_DEPRECATED, key)) {

				continue;
			}

			String value = null;

			if ((prefsPropsFilterString != null) &&
				ArrayUtil.contains(
					LegacySamlPropsKeys.SAML_KEYS_FILTERED, key)) {

				String prefsPropsKey = key + prefsPropsFilterString;

				value = portletPreferences.getValue(prefsPropsKey, null);

				if (value != null) {
					migratedPrefsPropsKeys.add(prefsPropsKey);
				}
			}

			if (value == null) {
				value = portletPreferences.getValue(key, null);

				if (value != null) {
					migratedPrefsPropsKeys.add(key);
				}
			}

			if (value == null) {
				value = getPropsValue(_props, key, propsFilter);
			}

			if (value == null) {
				continue;
			}

			String defaultValue = getDefaultValue(key);

			if (!Objects.equals(value, defaultValue)) {
				properties.put(key, value);
			}
		}

		if (!migratedPrefsPropsKeys.isEmpty()) {
			long companyThreadLocalCompanyId =
				CompanyThreadLocal.getCompanyId();

			try {
				CompanyThreadLocal.setCompanyId(companyId);

				_samlProviderConfigurationHelper.updateProperties(properties);
			}
			finally {
				CompanyThreadLocal.setCompanyId(companyThreadLocalCompanyId);
			}
		}

		return migratedPrefsPropsKeys;
	}

	public void migrateSAMLProviderConfigurationSystemPreferences()
		throws Exception {

		Filter filter = null;

		String entityId = _props.get(LegacySamlPropsKeys.SAML_ENTITY_ID);

		if (Validator.isNotNull(entityId)) {
			filter = new Filter(entityId);
		}

		UnicodeProperties properties = new UnicodeProperties();

		for (String key : LegacySamlPropsKeys.SAML_KEYS_PREFS_PROPS) {
			if (ArrayUtil.contains(
					LegacySamlPropsKeys.SAML_KEYS_DEPRECATED, key)) {

				continue;
			}

			String value = getPropsValue(_props, key, filter);

			if (value == null) {
				continue;
			}

			String defaultValue = getDefaultValue(key);

			if (!Objects.equals(value, defaultValue)) {
				properties.put(key, value);
			}
		}

		if (!properties.isEmpty()) {
			long companyThreadLocalCompanyId =
				CompanyThreadLocal.getCompanyId();

			try {
				CompanyThreadLocal.setCompanyId(CompanyConstants.SYSTEM);

				_samlProviderConfigurationHelper.updateProperties(properties);
			}
			finally {
				CompanyThreadLocal.setCompanyId(companyThreadLocalCompanyId);
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<Company> companies = _companyLocalService.getCompanies(false);

			for (Company company : companies) {
				Set<String> migratedPrefsPropsKeys =
					migrateSAMLProviderConfigurationPreferences(
						company.getCompanyId());

				if (migratedPrefsPropsKeys.isEmpty()) {
					continue;
				}

				_companyLocalService.removePreferences(
					company.getCompanyId(),
					migratedPrefsPropsKeys.toArray(
						new String[migratedPrefsPropsKeys.size()]));
			}

			migrateSAMLProviderConfigurationSystemPreferences();
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final PrefsProps _prefsProps;
	private final Props _props;
	private final SamlProviderConfigurationHelper
		_samlProviderConfigurationHelper;

}