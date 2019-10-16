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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.saml.opensaml.integration.resolver.AttributeResolver;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = AttributeResolverRegistry.class)
public class AttributeResolverRegistry {

	public AttributeResolver getAttributeResolver(String entityId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		AttributeResolver attributeResolver = _attributeResolvers.getService(
			companyId + "," + entityId);

		if (attributeResolver == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No attribute resolver for company ID " + companyId +
						" and entity ID " + entityId);
			}

			attributeResolver = _defaultAttributeResolver;
		}

		return attributeResolver;
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(!(companyId=*))", unbind = "-"
	)
	public void setDefaultAttributeResolver(
		AttributeResolver defaultAttributeResolver) {

		_defaultAttributeResolver = defaultAttributeResolver;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_attributeResolvers = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AttributeResolver.class, "(companyId=*)",
			new DefaultServiceReferenceMapper(_log));
	}

	@Deactivate
	protected void deactivate() {
		_attributeResolvers.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AttributeResolverRegistry.class);

	private ServiceTrackerMap<String, AttributeResolver> _attributeResolvers;
	private AttributeResolver _defaultAttributeResolver;

}