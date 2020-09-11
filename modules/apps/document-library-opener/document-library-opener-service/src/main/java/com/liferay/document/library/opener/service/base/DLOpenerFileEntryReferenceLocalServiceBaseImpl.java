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

package com.liferay.document.library.opener.service.base;

import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.document.library.opener.service.persistence.DLOpenerFileEntryReferencePersistence;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the dl opener file entry reference local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.document.library.opener.service.impl.DLOpenerFileEntryReferenceLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.document.library.opener.service.impl.DLOpenerFileEntryReferenceLocalServiceImpl
 * @generated
 */
public abstract class DLOpenerFileEntryReferenceLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, DLOpenerFileEntryReferenceLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>DLOpenerFileEntryReferenceLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalServiceUtil</code>.
	 */

	/**
	 * Adds the dl opener file entry reference to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DLOpenerFileEntryReferenceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dlOpenerFileEntryReference the dl opener file entry reference
	 * @return the dl opener file entry reference that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLOpenerFileEntryReference addDLOpenerFileEntryReference(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {

		dlOpenerFileEntryReference.setNew(true);

		return dlOpenerFileEntryReferencePersistence.update(
			dlOpenerFileEntryReference);
	}

	/**
	 * Creates a new dl opener file entry reference with the primary key. Does not add the dl opener file entry reference to the database.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key for the new dl opener file entry reference
	 * @return the new dl opener file entry reference
	 */
	@Override
	@Transactional(enabled = false)
	public DLOpenerFileEntryReference createDLOpenerFileEntryReference(
		long dlOpenerFileEntryReferenceId) {

		return dlOpenerFileEntryReferencePersistence.create(
			dlOpenerFileEntryReferenceId);
	}

	/**
	 * Deletes the dl opener file entry reference with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DLOpenerFileEntryReferenceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference that was removed
	 * @throws PortalException if a dl opener file entry reference with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLOpenerFileEntryReference deleteDLOpenerFileEntryReference(
			long dlOpenerFileEntryReferenceId)
		throws PortalException {

		return dlOpenerFileEntryReferencePersistence.remove(
			dlOpenerFileEntryReferenceId);
	}

	/**
	 * Deletes the dl opener file entry reference from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DLOpenerFileEntryReferenceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dlOpenerFileEntryReference the dl opener file entry reference
	 * @return the dl opener file entry reference that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLOpenerFileEntryReference deleteDLOpenerFileEntryReference(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {

		return dlOpenerFileEntryReferencePersistence.remove(
			dlOpenerFileEntryReference);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return dlOpenerFileEntryReferencePersistence.dslQuery(dslQuery);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			DLOpenerFileEntryReference.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return dlOpenerFileEntryReferencePersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.opener.model.impl.DLOpenerFileEntryReferenceModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return dlOpenerFileEntryReferencePersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.opener.model.impl.DLOpenerFileEntryReferenceModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return dlOpenerFileEntryReferencePersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return dlOpenerFileEntryReferencePersistence.countWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return dlOpenerFileEntryReferencePersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public DLOpenerFileEntryReference fetchDLOpenerFileEntryReference(
		long dlOpenerFileEntryReferenceId) {

		return dlOpenerFileEntryReferencePersistence.fetchByPrimaryKey(
			dlOpenerFileEntryReferenceId);
	}

	/**
	 * Returns the dl opener file entry reference with the primary key.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference
	 * @throws PortalException if a dl opener file entry reference with the primary key could not be found
	 */
	@Override
	public DLOpenerFileEntryReference getDLOpenerFileEntryReference(
			long dlOpenerFileEntryReferenceId)
		throws PortalException {

		return dlOpenerFileEntryReferencePersistence.findByPrimaryKey(
			dlOpenerFileEntryReferenceId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			dlOpenerFileEntryReferenceLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DLOpenerFileEntryReference.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"dlOpenerFileEntryReferenceId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			dlOpenerFileEntryReferenceLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			DLOpenerFileEntryReference.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"dlOpenerFileEntryReferenceId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			dlOpenerFileEntryReferenceLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DLOpenerFileEntryReference.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"dlOpenerFileEntryReferenceId");
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return dlOpenerFileEntryReferencePersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return dlOpenerFileEntryReferenceLocalService.
			deleteDLOpenerFileEntryReference(
				(DLOpenerFileEntryReference)persistedModel);
	}

	public BasePersistence<DLOpenerFileEntryReference> getBasePersistence() {
		return dlOpenerFileEntryReferencePersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return dlOpenerFileEntryReferencePersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the dl opener file entry references.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.opener.model.impl.DLOpenerFileEntryReferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl opener file entry references
	 * @param end the upper bound of the range of dl opener file entry references (not inclusive)
	 * @return the range of dl opener file entry references
	 */
	@Override
	public List<DLOpenerFileEntryReference> getDLOpenerFileEntryReferences(
		int start, int end) {

		return dlOpenerFileEntryReferencePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of dl opener file entry references.
	 *
	 * @return the number of dl opener file entry references
	 */
	@Override
	public int getDLOpenerFileEntryReferencesCount() {
		return dlOpenerFileEntryReferencePersistence.countAll();
	}

	/**
	 * Updates the dl opener file entry reference in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DLOpenerFileEntryReferenceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dlOpenerFileEntryReference the dl opener file entry reference
	 * @return the dl opener file entry reference that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLOpenerFileEntryReference updateDLOpenerFileEntryReference(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {

		return dlOpenerFileEntryReferencePersistence.update(
			dlOpenerFileEntryReference);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			DLOpenerFileEntryReferenceLocalService.class,
			IdentifiableOSGiService.class, PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		dlOpenerFileEntryReferenceLocalService =
			(DLOpenerFileEntryReferenceLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DLOpenerFileEntryReferenceLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DLOpenerFileEntryReference.class;
	}

	protected String getModelClassName() {
		return DLOpenerFileEntryReference.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				dlOpenerFileEntryReferencePersistence.getDataSource();

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

	protected DLOpenerFileEntryReferenceLocalService
		dlOpenerFileEntryReferenceLocalService;

	@Reference
	protected DLOpenerFileEntryReferencePersistence
		dlOpenerFileEntryReferencePersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

}