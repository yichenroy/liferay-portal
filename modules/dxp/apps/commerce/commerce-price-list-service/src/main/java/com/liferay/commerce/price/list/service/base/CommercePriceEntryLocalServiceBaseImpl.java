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

package com.liferay.commerce.price.list.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.persistence.CommercePriceEntryPersistence;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListFinder;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListPersistence;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListUserSegmentEntryRelPersistence;
import com.liferay.commerce.price.list.service.persistence.CommerceTierPriceEntryPersistence;

import com.liferay.expando.kernel.service.persistence.ExpandoRowPersistence;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
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
 * Provides the base implementation for the commerce price entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.price.list.service.impl.CommercePriceEntryLocalServiceImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.price.list.service.impl.CommercePriceEntryLocalServiceImpl
 * @see com.liferay.commerce.price.list.service.CommercePriceEntryLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class CommercePriceEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements CommercePriceEntryLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.commerce.price.list.service.CommercePriceEntryLocalServiceUtil} to access the commerce price entry local service.
	 */

	/**
	 * Adds the commerce price entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceEntry the commerce price entry
	 * @return the commerce price entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry addCommercePriceEntry(
		CommercePriceEntry commercePriceEntry) {
		commercePriceEntry.setNew(true);

		return commercePriceEntryPersistence.update(commercePriceEntry);
	}

	/**
	 * Creates a new commerce price entry with the primary key. Does not add the commerce price entry to the database.
	 *
	 * @param commercePriceEntryId the primary key for the new commerce price entry
	 * @return the new commerce price entry
	 */
	@Override
	@Transactional(enabled = false)
	public CommercePriceEntry createCommercePriceEntry(
		long commercePriceEntryId) {
		return commercePriceEntryPersistence.create(commercePriceEntryId);
	}

	/**
	 * Deletes the commerce price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceEntryId the primary key of the commerce price entry
	 * @return the commerce price entry that was removed
	 * @throws PortalException if a commerce price entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommercePriceEntry deleteCommercePriceEntry(
		long commercePriceEntryId) throws PortalException {
		return commercePriceEntryPersistence.remove(commercePriceEntryId);
	}

	/**
	 * Deletes the commerce price entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceEntry the commerce price entry
	 * @return the commerce price entry that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommercePriceEntry deleteCommercePriceEntry(
		CommercePriceEntry commercePriceEntry) throws PortalException {
		return commercePriceEntryPersistence.remove(commercePriceEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(CommercePriceEntry.class,
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
		return commercePriceEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return commercePriceEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return commercePriceEntryPersistence.findWithDynamicQuery(dynamicQuery,
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
		return commercePriceEntryPersistence.countWithDynamicQuery(dynamicQuery);
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
		return commercePriceEntryPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public CommercePriceEntry fetchCommercePriceEntry(long commercePriceEntryId) {
		return commercePriceEntryPersistence.fetchByPrimaryKey(commercePriceEntryId);
	}

	/**
	 * Returns the commerce price entry matching the UUID and group.
	 *
	 * @param uuid the commerce price entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchCommercePriceEntryByUuidAndGroupId(
		String uuid, long groupId) {
		return commercePriceEntryPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce price entry with the primary key.
	 *
	 * @param commercePriceEntryId the primary key of the commerce price entry
	 * @return the commerce price entry
	 * @throws PortalException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry getCommercePriceEntry(long commercePriceEntryId)
		throws PortalException {
		return commercePriceEntryPersistence.findByPrimaryKey(commercePriceEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(commercePriceEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommercePriceEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("commercePriceEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(commercePriceEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(CommercePriceEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"commercePriceEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(commercePriceEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommercePriceEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("commercePriceEntryId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommercePriceEntry>() {
				@Override
				public void performAction(CommercePriceEntry commercePriceEntry)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						commercePriceEntry);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(CommercePriceEntry.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return commercePriceEntryLocalService.deleteCommercePriceEntry((CommercePriceEntry)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return commercePriceEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the commerce price entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the commerce price entries
	 * @param companyId the primary key of the company
	 * @return the matching commerce price entries, or an empty list if no matches were found
	 */
	@Override
	public List<CommercePriceEntry> getCommercePriceEntriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return commercePriceEntryPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of commerce price entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the commerce price entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching commerce price entries, or an empty list if no matches were found
	 */
	@Override
	public List<CommercePriceEntry> getCommercePriceEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return commercePriceEntryPersistence.findByUuid_C(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	 * Returns the commerce price entry matching the UUID and group.
	 *
	 * @param uuid the commerce price entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce price entry
	 * @throws PortalException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry getCommercePriceEntryByUuidAndGroupId(
		String uuid, long groupId) throws PortalException {
		return commercePriceEntryPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the commerce price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> getCommercePriceEntries(int start, int end) {
		return commercePriceEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of commerce price entries.
	 *
	 * @return the number of commerce price entries
	 */
	@Override
	public int getCommercePriceEntriesCount() {
		return commercePriceEntryPersistence.countAll();
	}

	/**
	 * Updates the commerce price entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceEntry the commerce price entry
	 * @return the commerce price entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry updateCommercePriceEntry(
		CommercePriceEntry commercePriceEntry) {
		return commercePriceEntryPersistence.update(commercePriceEntry);
	}

	/**
	 * Returns the commerce price entry local service.
	 *
	 * @return the commerce price entry local service
	 */
	public CommercePriceEntryLocalService getCommercePriceEntryLocalService() {
		return commercePriceEntryLocalService;
	}

	/**
	 * Sets the commerce price entry local service.
	 *
	 * @param commercePriceEntryLocalService the commerce price entry local service
	 */
	public void setCommercePriceEntryLocalService(
		CommercePriceEntryLocalService commercePriceEntryLocalService) {
		this.commercePriceEntryLocalService = commercePriceEntryLocalService;
	}

	/**
	 * Returns the commerce price entry persistence.
	 *
	 * @return the commerce price entry persistence
	 */
	public CommercePriceEntryPersistence getCommercePriceEntryPersistence() {
		return commercePriceEntryPersistence;
	}

	/**
	 * Sets the commerce price entry persistence.
	 *
	 * @param commercePriceEntryPersistence the commerce price entry persistence
	 */
	public void setCommercePriceEntryPersistence(
		CommercePriceEntryPersistence commercePriceEntryPersistence) {
		this.commercePriceEntryPersistence = commercePriceEntryPersistence;
	}

	/**
	 * Returns the commerce price list local service.
	 *
	 * @return the commerce price list local service
	 */
	public com.liferay.commerce.price.list.service.CommercePriceListLocalService getCommercePriceListLocalService() {
		return commercePriceListLocalService;
	}

	/**
	 * Sets the commerce price list local service.
	 *
	 * @param commercePriceListLocalService the commerce price list local service
	 */
	public void setCommercePriceListLocalService(
		com.liferay.commerce.price.list.service.CommercePriceListLocalService commercePriceListLocalService) {
		this.commercePriceListLocalService = commercePriceListLocalService;
	}

	/**
	 * Returns the commerce price list persistence.
	 *
	 * @return the commerce price list persistence
	 */
	public CommercePriceListPersistence getCommercePriceListPersistence() {
		return commercePriceListPersistence;
	}

	/**
	 * Sets the commerce price list persistence.
	 *
	 * @param commercePriceListPersistence the commerce price list persistence
	 */
	public void setCommercePriceListPersistence(
		CommercePriceListPersistence commercePriceListPersistence) {
		this.commercePriceListPersistence = commercePriceListPersistence;
	}

	/**
	 * Returns the commerce price list finder.
	 *
	 * @return the commerce price list finder
	 */
	public CommercePriceListFinder getCommercePriceListFinder() {
		return commercePriceListFinder;
	}

	/**
	 * Sets the commerce price list finder.
	 *
	 * @param commercePriceListFinder the commerce price list finder
	 */
	public void setCommercePriceListFinder(
		CommercePriceListFinder commercePriceListFinder) {
		this.commercePriceListFinder = commercePriceListFinder;
	}

	/**
	 * Returns the commerce price list user segment entry rel local service.
	 *
	 * @return the commerce price list user segment entry rel local service
	 */
	public com.liferay.commerce.price.list.service.CommercePriceListUserSegmentEntryRelLocalService getCommercePriceListUserSegmentEntryRelLocalService() {
		return commercePriceListUserSegmentEntryRelLocalService;
	}

	/**
	 * Sets the commerce price list user segment entry rel local service.
	 *
	 * @param commercePriceListUserSegmentEntryRelLocalService the commerce price list user segment entry rel local service
	 */
	public void setCommercePriceListUserSegmentEntryRelLocalService(
		com.liferay.commerce.price.list.service.CommercePriceListUserSegmentEntryRelLocalService commercePriceListUserSegmentEntryRelLocalService) {
		this.commercePriceListUserSegmentEntryRelLocalService = commercePriceListUserSegmentEntryRelLocalService;
	}

	/**
	 * Returns the commerce price list user segment entry rel persistence.
	 *
	 * @return the commerce price list user segment entry rel persistence
	 */
	public CommercePriceListUserSegmentEntryRelPersistence getCommercePriceListUserSegmentEntryRelPersistence() {
		return commercePriceListUserSegmentEntryRelPersistence;
	}

	/**
	 * Sets the commerce price list user segment entry rel persistence.
	 *
	 * @param commercePriceListUserSegmentEntryRelPersistence the commerce price list user segment entry rel persistence
	 */
	public void setCommercePriceListUserSegmentEntryRelPersistence(
		CommercePriceListUserSegmentEntryRelPersistence commercePriceListUserSegmentEntryRelPersistence) {
		this.commercePriceListUserSegmentEntryRelPersistence = commercePriceListUserSegmentEntryRelPersistence;
	}

	/**
	 * Returns the commerce tier price entry local service.
	 *
	 * @return the commerce tier price entry local service
	 */
	public com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService getCommerceTierPriceEntryLocalService() {
		return commerceTierPriceEntryLocalService;
	}

	/**
	 * Sets the commerce tier price entry local service.
	 *
	 * @param commerceTierPriceEntryLocalService the commerce tier price entry local service
	 */
	public void setCommerceTierPriceEntryLocalService(
		com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService commerceTierPriceEntryLocalService) {
		this.commerceTierPriceEntryLocalService = commerceTierPriceEntryLocalService;
	}

	/**
	 * Returns the commerce tier price entry persistence.
	 *
	 * @return the commerce tier price entry persistence
	 */
	public CommerceTierPriceEntryPersistence getCommerceTierPriceEntryPersistence() {
		return commerceTierPriceEntryPersistence;
	}

	/**
	 * Sets the commerce tier price entry persistence.
	 *
	 * @param commerceTierPriceEntryPersistence the commerce tier price entry persistence
	 */
	public void setCommerceTierPriceEntryPersistence(
		CommerceTierPriceEntryPersistence commerceTierPriceEntryPersistence) {
		this.commerceTierPriceEntryPersistence = commerceTierPriceEntryPersistence;
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

	/**
	 * Returns the expando row local service.
	 *
	 * @return the expando row local service
	 */
	public com.liferay.expando.kernel.service.ExpandoRowLocalService getExpandoRowLocalService() {
		return expandoRowLocalService;
	}

	/**
	 * Sets the expando row local service.
	 *
	 * @param expandoRowLocalService the expando row local service
	 */
	public void setExpandoRowLocalService(
		com.liferay.expando.kernel.service.ExpandoRowLocalService expandoRowLocalService) {
		this.expandoRowLocalService = expandoRowLocalService;
	}

	/**
	 * Returns the expando row persistence.
	 *
	 * @return the expando row persistence
	 */
	public ExpandoRowPersistence getExpandoRowPersistence() {
		return expandoRowPersistence;
	}

	/**
	 * Sets the expando row persistence.
	 *
	 * @param expandoRowPersistence the expando row persistence
	 */
	public void setExpandoRowPersistence(
		ExpandoRowPersistence expandoRowPersistence) {
		this.expandoRowPersistence = expandoRowPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.commerce.price.list.model.CommercePriceEntry",
			commercePriceEntryLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.price.list.model.CommercePriceEntry");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommercePriceEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommercePriceEntry.class;
	}

	protected String getModelClassName() {
		return CommercePriceEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = commercePriceEntryPersistence.getDataSource();

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

	@BeanReference(type = CommercePriceEntryLocalService.class)
	protected CommercePriceEntryLocalService commercePriceEntryLocalService;
	@BeanReference(type = CommercePriceEntryPersistence.class)
	protected CommercePriceEntryPersistence commercePriceEntryPersistence;
	@BeanReference(type = com.liferay.commerce.price.list.service.CommercePriceListLocalService.class)
	protected com.liferay.commerce.price.list.service.CommercePriceListLocalService commercePriceListLocalService;
	@BeanReference(type = CommercePriceListPersistence.class)
	protected CommercePriceListPersistence commercePriceListPersistence;
	@BeanReference(type = CommercePriceListFinder.class)
	protected CommercePriceListFinder commercePriceListFinder;
	@BeanReference(type = com.liferay.commerce.price.list.service.CommercePriceListUserSegmentEntryRelLocalService.class)
	protected com.liferay.commerce.price.list.service.CommercePriceListUserSegmentEntryRelLocalService commercePriceListUserSegmentEntryRelLocalService;
	@BeanReference(type = CommercePriceListUserSegmentEntryRelPersistence.class)
	protected CommercePriceListUserSegmentEntryRelPersistence commercePriceListUserSegmentEntryRelPersistence;
	@BeanReference(type = com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService.class)
	protected com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService commerceTierPriceEntryLocalService;
	@BeanReference(type = CommerceTierPriceEntryPersistence.class)
	protected CommerceTierPriceEntryPersistence commerceTierPriceEntryPersistence;
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
	@ServiceReference(type = com.liferay.expando.kernel.service.ExpandoRowLocalService.class)
	protected com.liferay.expando.kernel.service.ExpandoRowLocalService expandoRowLocalService;
	@ServiceReference(type = ExpandoRowPersistence.class)
	protected ExpandoRowPersistence expandoRowPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}