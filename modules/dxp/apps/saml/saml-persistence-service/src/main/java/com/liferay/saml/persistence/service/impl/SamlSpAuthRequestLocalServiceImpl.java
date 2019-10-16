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

package com.liferay.saml.persistence.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.saml.persistence.model.SamlSpAuthRequest;
import com.liferay.saml.persistence.service.base.SamlSpAuthRequestLocalServiceBaseImpl;
import com.liferay.saml.persistence.service.util.SamlConfigurationUtil;
import com.liferay.saml.runtime.configuration.SamlConfiguration;

import java.util.Date;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Mika Koivisto
 */
public class SamlSpAuthRequestLocalServiceImpl
	extends SamlSpAuthRequestLocalServiceBaseImpl {

	@Override
	public SamlSpAuthRequest addSamlSpAuthRequest(
		String samlIdpEntityId, String samlSpAuthRequestKey,
		ServiceContext serviceContext) {

		long samlSpAuthRequestId = counterLocalService.increment(
			SamlSpAuthRequest.class.getName());

		SamlSpAuthRequest samlSpAuthRequest =
			samlSpAuthRequestPersistence.create(samlSpAuthRequestId);

		samlSpAuthRequest.setCompanyId(serviceContext.getCompanyId());
		samlSpAuthRequest.setCreateDate(new Date());
		samlSpAuthRequest.setSamlIdpEntityId(samlIdpEntityId);
		samlSpAuthRequest.setSamlSpAuthRequestKey(samlSpAuthRequestKey);

		samlSpAuthRequestPersistence.update(samlSpAuthRequest);

		return samlSpAuthRequest;
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		_samlConfiguration = SamlConfigurationUtil.getSamlConfiguration(
			_configurationAdmin);
	}

	@Override
	public void deleteExpiredSamlSpAuthRequests() {
		Date createDate = new Date();

		createDate.setTime(
			createDate.getTime() - _samlConfiguration.getSpAuthRequestMaxAge());

		samlSpAuthRequestPersistence.removeByCreateDate(createDate);
	}

	@Override
	public SamlSpAuthRequest fetchSamlSpAuthRequest(
		String samlIdpEntityId, String samlSpAuthRequestKey) {

		return samlSpAuthRequestPersistence.fetchBySIEI_SSARK(
			samlIdpEntityId, samlSpAuthRequestKey);
	}

	@Override
	public SamlSpAuthRequest getSamlSpAuthRequest(
			String samlIdpEntityId, String samlSpAuthRequestKey)
		throws PortalException {

		return samlSpAuthRequestPersistence.findBySIEI_SSARK(
			samlIdpEntityId, samlSpAuthRequestKey);
	}

	@ServiceReference(type = ConfigurationAdmin.class)
	private ConfigurationAdmin _configurationAdmin;

	private SamlConfiguration _samlConfiguration;

}