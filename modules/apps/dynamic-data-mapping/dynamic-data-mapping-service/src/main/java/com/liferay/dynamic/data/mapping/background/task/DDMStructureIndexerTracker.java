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

package com.liferay.dynamic.data.mapping.background.task;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.DDMStructureIndexer;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Igor Fabiano Nazar
 * @author Lucas Marques de Paula
 */
@Component(immediate = true, service = DDMStructureIndexerTracker.class)
public class DDMStructureIndexerTracker {

	public DDMStructureIndexer getDDMStructureIndexer(String className)
		throws PortalException {

		ServiceWrapper<DDMStructureIndexer> serviceWrapper =
			_serviceTrackerMap.getService(className);

		if (serviceWrapper == null) {
			throw new PortalException(
				"DDMStructureIndexer not found, for: " + className);
		}

		DDMStructureIndexer ddmStructureIndexer = serviceWrapper.getService();

		return ddmStructureIndexer;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDMStructureIndexer.class,
			"ddm.structure.indexer.class.name",
			ServiceTrackerCustomizerFactory.<DDMStructureIndexer>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ServiceWrapper<DDMStructureIndexer>>
		_serviceTrackerMap;

}