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

package com.liferay.portal.reports.engine.console.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for Source. This utility wraps
 * {@link com.liferay.portal.reports.engine.console.service.impl.SourceServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SourceService
 * @see com.liferay.portal.reports.engine.console.service.base.SourceServiceBaseImpl
 * @see com.liferay.portal.reports.engine.console.service.impl.SourceServiceImpl
 * @generated
 */
@ProviderType
public class SourceServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.reports.engine.console.service.impl.SourceServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.reports.engine.console.model.Source addSource(
		long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String driverClassName, java.lang.String driverUrl,
		java.lang.String driverUserName, java.lang.String driverPassword,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSource(groupId, nameMap, driverClassName, driverUrl,
			driverUserName, driverPassword, serviceContext);
	}

	public static com.liferay.portal.reports.engine.console.model.Source deleteSource(
		long sourceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSource(sourceId);
	}

	public static com.liferay.portal.reports.engine.console.model.Source getSource(
		long sourceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSource(sourceId);
	}

	public static com.liferay.portal.reports.engine.console.model.Source updateSource(
		long sourceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String driverClassName, java.lang.String driverUrl,
		java.lang.String driverUserName, java.lang.String driverPassword,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSource(sourceId, nameMap, driverClassName, driverUrl,
			driverUserName, driverPassword, serviceContext);
	}

	public static int getSourcesCount(long groupId, java.lang.String name,
		java.lang.String driverUrl, boolean andSearch) {
		return getService().getSourcesCount(groupId, name, driverUrl, andSearch);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.portal.reports.engine.console.model.Source> getSources(
		long groupId, java.lang.String name, java.lang.String driverUrl,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getSources(groupId, name, driverUrl, andSearch, start, end,
			orderByComparator);
	}

	public static SourceService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SourceService, SourceService> _serviceTracker = ServiceTrackerFactory.open(SourceService.class);
}