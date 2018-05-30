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

package com.liferay.commerce.forecast.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.forecast.model.CommerceForecastValue;
import com.liferay.commerce.forecast.service.CommerceForecastValueLocalService;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastEntryPersistence;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastValueFinder;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastValuePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the commerce forecast value local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.forecast.service.impl.CommerceForecastValueLocalServiceImpl}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.forecast.service.impl.CommerceForecastValueLocalServiceImpl
 * @see com.liferay.commerce.forecast.service.CommerceForecastValueLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class CommerceForecastValueLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements CommerceForecastValueLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.commerce.forecast.service.CommerceForecastValueLocalServiceUtil} to access the commerce forecast value local service.
	 */

	/**
	 * Adds the commerce forecast value to the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceForecastValue the commerce forecast value
	 * @return the commerce forecast value that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceForecastValue addCommerceForecastValue(
		CommerceForecastValue commerceForecastValue) {
		commerceForecastValue.setNew(true);

		return commerceForecastValuePersistence.update(commerceForecastValue);
	}

	/**
	 * Creates a new commerce forecast value with the primary key. Does not add the commerce forecast value to the database.
	 *
	 * @param commerceForecastValueId the primary key for the new commerce forecast value
	 * @return the new commerce forecast value
	 */
	@Override
	@Transactional(enabled = false)
	public CommerceForecastValue createCommerceForecastValue(
		long commerceForecastValueId) {
		return commerceForecastValuePersistence.create(commerceForecastValueId);
	}

	/**
	 * Deletes the commerce forecast value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceForecastValueId the primary key of the commerce forecast value
	 * @return the commerce forecast value that was removed
	 * @throws PortalException if a commerce forecast value with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceForecastValue deleteCommerceForecastValue(
		long commerceForecastValueId) throws PortalException {
		return commerceForecastValuePersistence.remove(commerceForecastValueId);
	}

	/**
	 * Deletes the commerce forecast value from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceForecastValue the commerce forecast value
	 * @return the commerce forecast value that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceForecastValue deleteCommerceForecastValue(
		CommerceForecastValue commerceForecastValue) {
		return commerceForecastValuePersistence.remove(commerceForecastValue);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(CommerceForecastValue.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return commerceForecastValuePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return commerceForecastValuePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return commerceForecastValuePersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return commerceForecastValuePersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return commerceForecastValuePersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public CommerceForecastValue fetchCommerceForecastValue(
		long commerceForecastValueId) {
		return commerceForecastValuePersistence.fetchByPrimaryKey(commerceForecastValueId);
	}

	/**
	 * Returns the commerce forecast value with the primary key.
	 *
	 * @param commerceForecastValueId the primary key of the commerce forecast value
	 * @return the commerce forecast value
	 * @throws PortalException if a commerce forecast value with the primary key could not be found
	 */
	@Override
	public CommerceForecastValue getCommerceForecastValue(
		long commerceForecastValueId) throws PortalException {
		return commerceForecastValuePersistence.findByPrimaryKey(commerceForecastValueId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(commerceForecastValueLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceForecastValue.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceForecastValueId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(commerceForecastValueLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(CommerceForecastValue.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceForecastValueId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(commerceForecastValueLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceForecastValue.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceForecastValueId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return commerceForecastValueLocalService.deleteCommerceForecastValue((CommerceForecastValue)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return commerceForecastValuePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the commerce forecast values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce forecast values
	 * @param end the upper bound of the range of commerce forecast values (not inclusive)
	 * @return the range of commerce forecast values
	 */
	@Override
	public List<CommerceForecastValue> getCommerceForecastValues(int start,
		int end) {
		return commerceForecastValuePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of commerce forecast values.
	 *
	 * @return the number of commerce forecast values
	 */
	@Override
	public int getCommerceForecastValuesCount() {
		return commerceForecastValuePersistence.countAll();
	}

	/**
	 * Updates the commerce forecast value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param commerceForecastValue the commerce forecast value
	 * @return the commerce forecast value that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceForecastValue updateCommerceForecastValue(
		CommerceForecastValue commerceForecastValue) {
		return commerceForecastValuePersistence.update(commerceForecastValue);
	}

	/**
	 * Returns the commerce forecast entry local service.
	 *
	 * @return the commerce forecast entry local service
	 */
	public com.liferay.commerce.forecast.service.CommerceForecastEntryLocalService getCommerceForecastEntryLocalService() {
		return commerceForecastEntryLocalService;
	}

	/**
	 * Sets the commerce forecast entry local service.
	 *
	 * @param commerceForecastEntryLocalService the commerce forecast entry local service
	 */
	public void setCommerceForecastEntryLocalService(
		com.liferay.commerce.forecast.service.CommerceForecastEntryLocalService commerceForecastEntryLocalService) {
		this.commerceForecastEntryLocalService = commerceForecastEntryLocalService;
	}

	/**
	 * Returns the commerce forecast entry persistence.
	 *
	 * @return the commerce forecast entry persistence
	 */
	public CommerceForecastEntryPersistence getCommerceForecastEntryPersistence() {
		return commerceForecastEntryPersistence;
	}

	/**
	 * Sets the commerce forecast entry persistence.
	 *
	 * @param commerceForecastEntryPersistence the commerce forecast entry persistence
	 */
	public void setCommerceForecastEntryPersistence(
		CommerceForecastEntryPersistence commerceForecastEntryPersistence) {
		this.commerceForecastEntryPersistence = commerceForecastEntryPersistence;
	}

	/**
	 * Returns the commerce forecast value local service.
	 *
	 * @return the commerce forecast value local service
	 */
	public CommerceForecastValueLocalService getCommerceForecastValueLocalService() {
		return commerceForecastValueLocalService;
	}

	/**
	 * Sets the commerce forecast value local service.
	 *
	 * @param commerceForecastValueLocalService the commerce forecast value local service
	 */
	public void setCommerceForecastValueLocalService(
		CommerceForecastValueLocalService commerceForecastValueLocalService) {
		this.commerceForecastValueLocalService = commerceForecastValueLocalService;
	}

	/**
	 * Returns the commerce forecast value persistence.
	 *
	 * @return the commerce forecast value persistence
	 */
	public CommerceForecastValuePersistence getCommerceForecastValuePersistence() {
		return commerceForecastValuePersistence;
	}

	/**
	 * Sets the commerce forecast value persistence.
	 *
	 * @param commerceForecastValuePersistence the commerce forecast value persistence
	 */
	public void setCommerceForecastValuePersistence(
		CommerceForecastValuePersistence commerceForecastValuePersistence) {
		this.commerceForecastValuePersistence = commerceForecastValuePersistence;
	}

	/**
	 * Returns the commerce forecast value finder.
	 *
	 * @return the commerce forecast value finder
	 */
	public CommerceForecastValueFinder getCommerceForecastValueFinder() {
		return commerceForecastValueFinder;
	}

	/**
	 * Sets the commerce forecast value finder.
	 *
	 * @param commerceForecastValueFinder the commerce forecast value finder
	 */
	public void setCommerceForecastValueFinder(
		CommerceForecastValueFinder commerceForecastValueFinder) {
		this.commerceForecastValueFinder = commerceForecastValueFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.commerce.forecast.model.CommerceForecastValue",
			commerceForecastValueLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.forecast.model.CommerceForecastValue");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceForecastValueLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceForecastValue.class;
	}

	protected String getModelClassName() {
		return CommerceForecastValue.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = commerceForecastValuePersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.commerce.forecast.service.CommerceForecastEntryLocalService.class)
	protected com.liferay.commerce.forecast.service.CommerceForecastEntryLocalService commerceForecastEntryLocalService;
	@BeanReference(type = CommerceForecastEntryPersistence.class)
	protected CommerceForecastEntryPersistence commerceForecastEntryPersistence;
	@BeanReference(type = CommerceForecastValueLocalService.class)
	protected CommerceForecastValueLocalService commerceForecastValueLocalService;
	@BeanReference(type = CommerceForecastValuePersistence.class)
	protected CommerceForecastValuePersistence commerceForecastValuePersistence;
	@BeanReference(type = CommerceForecastValueFinder.class)
	protected CommerceForecastValueFinder commerceForecastValueFinder;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}