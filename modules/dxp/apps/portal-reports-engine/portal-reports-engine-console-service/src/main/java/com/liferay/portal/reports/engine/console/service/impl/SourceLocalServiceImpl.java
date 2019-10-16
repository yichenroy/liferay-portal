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

package com.liferay.portal.reports.engine.console.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.reports.engine.console.exception.SourceDriverClassNameException;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.base.SourceLocalServiceBaseImpl;
import com.liferay.portal.reports.engine.console.util.ReportsEngineConsoleUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Gavin Wan
 */
public class SourceLocalServiceImpl extends SourceLocalServiceBaseImpl {

	@Override
	public Source addSource(
			long userId, long groupId, Map<Locale, String> nameMap,
			String driverClassName, String driverUrl, String driverUserName,
			String driverPassword, ServiceContext serviceContext)
		throws PortalException {

		// Source

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validate(driverClassName, driverUrl, driverUserName, driverPassword);

		long sourceId = counterLocalService.increment();

		Source source = sourceLocalService.createSource(sourceId);

		source.setUuid(serviceContext.getUuid());
		source.setGroupId(groupId);
		source.setCompanyId(user.getCompanyId());
		source.setUserId(user.getUserId());
		source.setUserName(user.getFullName());
		source.setCreateDate(serviceContext.getCreateDate(now));
		source.setModifiedDate(serviceContext.getModifiedDate(now));
		source.setNameMap(nameMap);
		source.setDriverClassName(driverClassName);
		source.setDriverUrl(driverUrl);
		source.setDriverUserName(driverUserName);
		source.setDriverPassword(driverPassword);

		sourcePersistence.update(source);

		// Resources

		resourceLocalService.addModelResources(source, serviceContext);

		return source;
	}

	@Override
	public Source deleteSource(long sourceId) throws PortalException {
		Source source = sourcePersistence.findByPrimaryKey(sourceId);

		return sourceLocalService.deleteSource(source);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public Source deleteSource(Source source) throws PortalException {

		// Source

		sourcePersistence.remove(source);

		// Resources

		resourceLocalService.deleteResource(
			source.getCompanyId(), Source.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, source.getSourceId());

		return source;
	}

	@Override
	public void deleteSources(long groupId) throws PortalException {
		List<Source> sources = sourcePersistence.findByGroupId(groupId);

		for (Source source : sources) {
			sourceLocalService.deleteSource(source);
		}
	}

	@Override
	public Source getSource(long sourceId) throws PortalException {
		return sourcePersistence.findByPrimaryKey(sourceId);
	}

	@Override
	public List<Source> getSources(
		long groupId, String name, String driverUrl, boolean andSearch,
		int start, int end, OrderByComparator orderByComparator) {

		return sourceFinder.findByG_N_DU(
			groupId, name, driverUrl, andSearch, start, end, orderByComparator);
	}

	@Override
	public int getSourcesCount(
		long groupId, String name, String driverUrl, boolean andSearch) {

		return sourceFinder.countByG_N_DU(groupId, name, driverUrl, andSearch);
	}

	@Override
	public Source updateSource(
			long sourceId, Map<Locale, String> nameMap, String driverClassName,
			String driverUrl, String driverUserName, String driverPassword,
			ServiceContext serviceContext)
		throws PortalException {

		// Source

		Source source = sourcePersistence.findByPrimaryKey(sourceId);

		if (Validator.isNull(driverPassword)) {
			driverPassword = source.getDriverPassword();
		}

		validate(driverClassName, driverUrl, driverUserName, driverPassword);

		source.setModifiedDate(serviceContext.getModifiedDate(null));
		source.setNameMap(nameMap);
		source.setDriverClassName(driverClassName);
		source.setDriverUrl(driverUrl);
		source.setDriverUserName(driverUserName);
		source.setDriverPassword(driverPassword);

		sourcePersistence.update(source);

		return source;
	}

	protected void validate(
			String driverClassName, String driverUrl, String driverUserName,
			String driverPassword)
		throws PortalException {

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		try {
			Class.forName(driverClassName, true, portalClassLoader);
		}
		catch (ClassNotFoundException cnfe) {
			throw new SourceDriverClassNameException(cnfe);
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(portalClassLoader);

		try {
			ReportsEngineConsoleUtil.validateJDBCConnection(
				driverClassName, driverUrl, driverUserName, driverPassword);
		}
		finally {
			currentThread.setContextClassLoader(classLoader);
		}
	}

}