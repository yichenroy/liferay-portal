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

package com.liferay.portal.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.persistence.ResourcePermissionFinder;
import com.liferay.portal.kernel.service.persistence.ResourcePermissionPersistence;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the resource local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.ResourceLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.ResourceLocalServiceImpl
 * @generated
 */
public abstract class ResourceLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements IdentifiableOSGiService, ResourceLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>ResourceLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.portal.kernel.service.ResourceLocalServiceUtil</code>.
	 */

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {

		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource permission local service.
	 *
	 * @return the resource permission local service
	 */
	public com.liferay.portal.kernel.service.ResourcePermissionLocalService
		getResourcePermissionLocalService() {

		return resourcePermissionLocalService;
	}

	/**
	 * Sets the resource permission local service.
	 *
	 * @param resourcePermissionLocalService the resource permission local service
	 */
	public void setResourcePermissionLocalService(
		com.liferay.portal.kernel.service.ResourcePermissionLocalService
			resourcePermissionLocalService) {

		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	/**
	 * Returns the resource permission persistence.
	 *
	 * @return the resource permission persistence
	 */
	public ResourcePermissionPersistence getResourcePermissionPersistence() {
		return resourcePermissionPersistence;
	}

	/**
	 * Sets the resource permission persistence.
	 *
	 * @param resourcePermissionPersistence the resource permission persistence
	 */
	public void setResourcePermissionPersistence(
		ResourcePermissionPersistence resourcePermissionPersistence) {

		this.resourcePermissionPersistence = resourcePermissionPersistence;
	}

	/**
	 * Returns the resource permission finder.
	 *
	 * @return the resource permission finder
	 */
	public ResourcePermissionFinder getResourcePermissionFinder() {
		return resourcePermissionFinder;
	}

	/**
	 * Sets the resource permission finder.
	 *
	 * @param resourcePermissionFinder the resource permission finder
	 */
	public void setResourcePermissionFinder(
		ResourcePermissionFinder resourcePermissionFinder) {

		this.resourcePermissionFinder = resourcePermissionFinder;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return ResourceLocalService.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = InfrastructureUtil.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;

	@BeanReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@BeanReference(
		type = com.liferay.portal.kernel.service.ResourcePermissionLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourcePermissionLocalService
		resourcePermissionLocalService;

	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;

	@BeanReference(type = ResourcePermissionFinder.class)
	protected ResourcePermissionFinder resourcePermissionFinder;

}