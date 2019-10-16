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

package com.liferay.portal.reports.engine.console.internal.security.permission.resource;

import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsoleConstants;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leon Chi
 */
@Component(immediate = true, service = {})
public class EntryPermissionRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				Entry.class, Entry::getEntryId, _entryLocalService::getEntry,
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> {
				}),
			MapUtil.singletonDictionary(
				"model.class.name", Entry.class.getName()));
	}

	@Deactivate
	public void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private EntryLocalService _entryLocalService;

	@Reference(
		target = "(resource.name=" + ReportsEngineConsoleConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

}