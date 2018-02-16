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

package com.liferay.commerce.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeLocalService;
import com.liferay.commerce.service.persistence.CPDefinitionAvailabilityRangePersistence;
import com.liferay.commerce.service.persistence.CPDefinitionInventoryPersistence;
import com.liferay.commerce.service.persistence.CommerceAddressPersistence;
import com.liferay.commerce.service.persistence.CommerceAddressRestrictionPersistence;
import com.liferay.commerce.service.persistence.CommerceAvailabilityRangePersistence;
import com.liferay.commerce.service.persistence.CommerceCountryFinder;
import com.liferay.commerce.service.persistence.CommerceCountryPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderItemFinder;
import com.liferay.commerce.service.persistence.CommerceOrderItemPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderNotePersistence;
import com.liferay.commerce.service.persistence.CommerceOrderPaymentPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderPersistence;
import com.liferay.commerce.service.persistence.CommercePaymentMethodPersistence;
import com.liferay.commerce.service.persistence.CommercePriceEntryPersistence;
import com.liferay.commerce.service.persistence.CommercePriceListFinder;
import com.liferay.commerce.service.persistence.CommercePriceListPersistence;
import com.liferay.commerce.service.persistence.CommercePriceListQualificationTypeRelPersistence;
import com.liferay.commerce.service.persistence.CommerceRegionPersistence;
import com.liferay.commerce.service.persistence.CommerceShipmentItemPersistence;
import com.liferay.commerce.service.persistence.CommerceShipmentPersistence;
import com.liferay.commerce.service.persistence.CommerceShippingMethodPersistence;
import com.liferay.commerce.service.persistence.CommerceTierPriceEntryPersistence;
import com.liferay.commerce.service.persistence.CommerceWarehouseFinder;
import com.liferay.commerce.service.persistence.CommerceWarehouseItemFinder;
import com.liferay.commerce.service.persistence.CommerceWarehouseItemPersistence;
import com.liferay.commerce.service.persistence.CommerceWarehousePersistence;

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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the cp definition availability range local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeLocalServiceImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeLocalServiceImpl
 * @see com.liferay.commerce.service.CPDefinitionAvailabilityRangeLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class CPDefinitionAvailabilityRangeLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements CPDefinitionAvailabilityRangeLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.commerce.service.CPDefinitionAvailabilityRangeLocalServiceUtil} to access the cp definition availability range local service.
	 */

	/**
	 * Adds the cp definition availability range to the database. Also notifies the appropriate model listeners.
	 *
	 * @param cpDefinitionAvailabilityRange the cp definition availability range
	 * @return the cp definition availability range that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionAvailabilityRange addCPDefinitionAvailabilityRange(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		cpDefinitionAvailabilityRange.setNew(true);

		return cpDefinitionAvailabilityRangePersistence.update(cpDefinitionAvailabilityRange);
	}

	/**
	 * Creates a new cp definition availability range with the primary key. Does not add the cp definition availability range to the database.
	 *
	 * @param CPDefinitionAvailabilityRangeId the primary key for the new cp definition availability range
	 * @return the new cp definition availability range
	 */
	@Override
	public CPDefinitionAvailabilityRange createCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId) {
		return cpDefinitionAvailabilityRangePersistence.create(CPDefinitionAvailabilityRangeId);
	}

	/**
	 * Deletes the cp definition availability range with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	 * @return the cp definition availability range that was removed
	 * @throws PortalException if a cp definition availability range with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPDefinitionAvailabilityRange deleteCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId) throws PortalException {
		return cpDefinitionAvailabilityRangePersistence.remove(CPDefinitionAvailabilityRangeId);
	}

	/**
	 * Deletes the cp definition availability range from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cpDefinitionAvailabilityRange the cp definition availability range
	 * @return the cp definition availability range that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPDefinitionAvailabilityRange deleteCPDefinitionAvailabilityRange(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		return cpDefinitionAvailabilityRangePersistence.remove(cpDefinitionAvailabilityRange);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(CPDefinitionAvailabilityRange.class,
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
		return cpDefinitionAvailabilityRangePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return cpDefinitionAvailabilityRangePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return cpDefinitionAvailabilityRangePersistence.findWithDynamicQuery(dynamicQuery,
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
		return cpDefinitionAvailabilityRangePersistence.countWithDynamicQuery(dynamicQuery);
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
		return cpDefinitionAvailabilityRangePersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId) {
		return cpDefinitionAvailabilityRangePersistence.fetchByPrimaryKey(CPDefinitionAvailabilityRangeId);
	}

	/**
	 * Returns the cp definition availability range matching the UUID and group.
	 *
	 * @param uuid the cp definition availability range's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRangeByUuidAndGroupId(
		String uuid, long groupId) {
		return cpDefinitionAvailabilityRangePersistence.fetchByUUID_G(uuid,
			groupId);
	}

	/**
	 * Returns the cp definition availability range with the primary key.
	 *
	 * @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	 * @return the cp definition availability range
	 * @throws PortalException if a cp definition availability range with the primary key could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange getCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId) throws PortalException {
		return cpDefinitionAvailabilityRangePersistence.findByPrimaryKey(CPDefinitionAvailabilityRangeId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(cpDefinitionAvailabilityRangeLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CPDefinitionAvailabilityRange.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPDefinitionAvailabilityRangeId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(cpDefinitionAvailabilityRangeLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(CPDefinitionAvailabilityRange.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPDefinitionAvailabilityRangeId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(cpDefinitionAvailabilityRangeLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CPDefinitionAvailabilityRange.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPDefinitionAvailabilityRangeId");
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

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPDefinitionAvailabilityRange>() {
				@Override
				public void performAction(
					CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						cpDefinitionAvailabilityRange);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(
					CPDefinitionAvailabilityRange.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return cpDefinitionAvailabilityRangeLocalService.deleteCPDefinitionAvailabilityRange((CPDefinitionAvailabilityRange)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return cpDefinitionAvailabilityRangePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the cp definition availability ranges matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp definition availability ranges
	 * @param companyId the primary key of the company
	 * @return the matching cp definition availability ranges, or an empty list if no matches were found
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> getCPDefinitionAvailabilityRangesByUuidAndCompanyId(
		String uuid, long companyId) {
		return cpDefinitionAvailabilityRangePersistence.findByUuid_C(uuid,
			companyId);
	}

	/**
	 * Returns a range of cp definition availability ranges matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp definition availability ranges
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching cp definition availability ranges, or an empty list if no matches were found
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> getCPDefinitionAvailabilityRangesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return cpDefinitionAvailabilityRangePersistence.findByUuid_C(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the cp definition availability range matching the UUID and group.
	 *
	 * @param uuid the cp definition availability range's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp definition availability range
	 * @throws PortalException if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange getCPDefinitionAvailabilityRangeByUuidAndGroupId(
		String uuid, long groupId) throws PortalException {
		return cpDefinitionAvailabilityRangePersistence.findByUUID_G(uuid,
			groupId);
	}

	/**
	 * Returns a range of all the cp definition availability ranges.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @return the range of cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> getCPDefinitionAvailabilityRanges(
		int start, int end) {
		return cpDefinitionAvailabilityRangePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of cp definition availability ranges.
	 *
	 * @return the number of cp definition availability ranges
	 */
	@Override
	public int getCPDefinitionAvailabilityRangesCount() {
		return cpDefinitionAvailabilityRangePersistence.countAll();
	}

	/**
	 * Updates the cp definition availability range in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param cpDefinitionAvailabilityRange the cp definition availability range
	 * @return the cp definition availability range that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionAvailabilityRange updateCPDefinitionAvailabilityRange(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		return cpDefinitionAvailabilityRangePersistence.update(cpDefinitionAvailabilityRange);
	}

	/**
	 * Returns the commerce address local service.
	 *
	 * @return the commerce address local service
	 */
	public com.liferay.commerce.service.CommerceAddressLocalService getCommerceAddressLocalService() {
		return commerceAddressLocalService;
	}

	/**
	 * Sets the commerce address local service.
	 *
	 * @param commerceAddressLocalService the commerce address local service
	 */
	public void setCommerceAddressLocalService(
		com.liferay.commerce.service.CommerceAddressLocalService commerceAddressLocalService) {
		this.commerceAddressLocalService = commerceAddressLocalService;
	}

	/**
	 * Returns the commerce address persistence.
	 *
	 * @return the commerce address persistence
	 */
	public CommerceAddressPersistence getCommerceAddressPersistence() {
		return commerceAddressPersistence;
	}

	/**
	 * Sets the commerce address persistence.
	 *
	 * @param commerceAddressPersistence the commerce address persistence
	 */
	public void setCommerceAddressPersistence(
		CommerceAddressPersistence commerceAddressPersistence) {
		this.commerceAddressPersistence = commerceAddressPersistence;
	}

	/**
	 * Returns the commerce address restriction local service.
	 *
	 * @return the commerce address restriction local service
	 */
	public com.liferay.commerce.service.CommerceAddressRestrictionLocalService getCommerceAddressRestrictionLocalService() {
		return commerceAddressRestrictionLocalService;
	}

	/**
	 * Sets the commerce address restriction local service.
	 *
	 * @param commerceAddressRestrictionLocalService the commerce address restriction local service
	 */
	public void setCommerceAddressRestrictionLocalService(
		com.liferay.commerce.service.CommerceAddressRestrictionLocalService commerceAddressRestrictionLocalService) {
		this.commerceAddressRestrictionLocalService = commerceAddressRestrictionLocalService;
	}

	/**
	 * Returns the commerce address restriction persistence.
	 *
	 * @return the commerce address restriction persistence
	 */
	public CommerceAddressRestrictionPersistence getCommerceAddressRestrictionPersistence() {
		return commerceAddressRestrictionPersistence;
	}

	/**
	 * Sets the commerce address restriction persistence.
	 *
	 * @param commerceAddressRestrictionPersistence the commerce address restriction persistence
	 */
	public void setCommerceAddressRestrictionPersistence(
		CommerceAddressRestrictionPersistence commerceAddressRestrictionPersistence) {
		this.commerceAddressRestrictionPersistence = commerceAddressRestrictionPersistence;
	}

	/**
	 * Returns the commerce availability range local service.
	 *
	 * @return the commerce availability range local service
	 */
	public com.liferay.commerce.service.CommerceAvailabilityRangeLocalService getCommerceAvailabilityRangeLocalService() {
		return commerceAvailabilityRangeLocalService;
	}

	/**
	 * Sets the commerce availability range local service.
	 *
	 * @param commerceAvailabilityRangeLocalService the commerce availability range local service
	 */
	public void setCommerceAvailabilityRangeLocalService(
		com.liferay.commerce.service.CommerceAvailabilityRangeLocalService commerceAvailabilityRangeLocalService) {
		this.commerceAvailabilityRangeLocalService = commerceAvailabilityRangeLocalService;
	}

	/**
	 * Returns the commerce availability range persistence.
	 *
	 * @return the commerce availability range persistence
	 */
	public CommerceAvailabilityRangePersistence getCommerceAvailabilityRangePersistence() {
		return commerceAvailabilityRangePersistence;
	}

	/**
	 * Sets the commerce availability range persistence.
	 *
	 * @param commerceAvailabilityRangePersistence the commerce availability range persistence
	 */
	public void setCommerceAvailabilityRangePersistence(
		CommerceAvailabilityRangePersistence commerceAvailabilityRangePersistence) {
		this.commerceAvailabilityRangePersistence = commerceAvailabilityRangePersistence;
	}

	/**
	 * Returns the commerce country local service.
	 *
	 * @return the commerce country local service
	 */
	public com.liferay.commerce.service.CommerceCountryLocalService getCommerceCountryLocalService() {
		return commerceCountryLocalService;
	}

	/**
	 * Sets the commerce country local service.
	 *
	 * @param commerceCountryLocalService the commerce country local service
	 */
	public void setCommerceCountryLocalService(
		com.liferay.commerce.service.CommerceCountryLocalService commerceCountryLocalService) {
		this.commerceCountryLocalService = commerceCountryLocalService;
	}

	/**
	 * Returns the commerce country persistence.
	 *
	 * @return the commerce country persistence
	 */
	public CommerceCountryPersistence getCommerceCountryPersistence() {
		return commerceCountryPersistence;
	}

	/**
	 * Sets the commerce country persistence.
	 *
	 * @param commerceCountryPersistence the commerce country persistence
	 */
	public void setCommerceCountryPersistence(
		CommerceCountryPersistence commerceCountryPersistence) {
		this.commerceCountryPersistence = commerceCountryPersistence;
	}

	/**
	 * Returns the commerce country finder.
	 *
	 * @return the commerce country finder
	 */
	public CommerceCountryFinder getCommerceCountryFinder() {
		return commerceCountryFinder;
	}

	/**
	 * Sets the commerce country finder.
	 *
	 * @param commerceCountryFinder the commerce country finder
	 */
	public void setCommerceCountryFinder(
		CommerceCountryFinder commerceCountryFinder) {
		this.commerceCountryFinder = commerceCountryFinder;
	}

	/**
	 * Returns the commerce order local service.
	 *
	 * @return the commerce order local service
	 */
	public com.liferay.commerce.service.CommerceOrderLocalService getCommerceOrderLocalService() {
		return commerceOrderLocalService;
	}

	/**
	 * Sets the commerce order local service.
	 *
	 * @param commerceOrderLocalService the commerce order local service
	 */
	public void setCommerceOrderLocalService(
		com.liferay.commerce.service.CommerceOrderLocalService commerceOrderLocalService) {
		this.commerceOrderLocalService = commerceOrderLocalService;
	}

	/**
	 * Returns the commerce order persistence.
	 *
	 * @return the commerce order persistence
	 */
	public CommerceOrderPersistence getCommerceOrderPersistence() {
		return commerceOrderPersistence;
	}

	/**
	 * Sets the commerce order persistence.
	 *
	 * @param commerceOrderPersistence the commerce order persistence
	 */
	public void setCommerceOrderPersistence(
		CommerceOrderPersistence commerceOrderPersistence) {
		this.commerceOrderPersistence = commerceOrderPersistence;
	}

	/**
	 * Returns the commerce order item local service.
	 *
	 * @return the commerce order item local service
	 */
	public com.liferay.commerce.service.CommerceOrderItemLocalService getCommerceOrderItemLocalService() {
		return commerceOrderItemLocalService;
	}

	/**
	 * Sets the commerce order item local service.
	 *
	 * @param commerceOrderItemLocalService the commerce order item local service
	 */
	public void setCommerceOrderItemLocalService(
		com.liferay.commerce.service.CommerceOrderItemLocalService commerceOrderItemLocalService) {
		this.commerceOrderItemLocalService = commerceOrderItemLocalService;
	}

	/**
	 * Returns the commerce order item persistence.
	 *
	 * @return the commerce order item persistence
	 */
	public CommerceOrderItemPersistence getCommerceOrderItemPersistence() {
		return commerceOrderItemPersistence;
	}

	/**
	 * Sets the commerce order item persistence.
	 *
	 * @param commerceOrderItemPersistence the commerce order item persistence
	 */
	public void setCommerceOrderItemPersistence(
		CommerceOrderItemPersistence commerceOrderItemPersistence) {
		this.commerceOrderItemPersistence = commerceOrderItemPersistence;
	}

	/**
	 * Returns the commerce order item finder.
	 *
	 * @return the commerce order item finder
	 */
	public CommerceOrderItemFinder getCommerceOrderItemFinder() {
		return commerceOrderItemFinder;
	}

	/**
	 * Sets the commerce order item finder.
	 *
	 * @param commerceOrderItemFinder the commerce order item finder
	 */
	public void setCommerceOrderItemFinder(
		CommerceOrderItemFinder commerceOrderItemFinder) {
		this.commerceOrderItemFinder = commerceOrderItemFinder;
	}

	/**
	 * Returns the commerce order note local service.
	 *
	 * @return the commerce order note local service
	 */
	public com.liferay.commerce.service.CommerceOrderNoteLocalService getCommerceOrderNoteLocalService() {
		return commerceOrderNoteLocalService;
	}

	/**
	 * Sets the commerce order note local service.
	 *
	 * @param commerceOrderNoteLocalService the commerce order note local service
	 */
	public void setCommerceOrderNoteLocalService(
		com.liferay.commerce.service.CommerceOrderNoteLocalService commerceOrderNoteLocalService) {
		this.commerceOrderNoteLocalService = commerceOrderNoteLocalService;
	}

	/**
	 * Returns the commerce order note persistence.
	 *
	 * @return the commerce order note persistence
	 */
	public CommerceOrderNotePersistence getCommerceOrderNotePersistence() {
		return commerceOrderNotePersistence;
	}

	/**
	 * Sets the commerce order note persistence.
	 *
	 * @param commerceOrderNotePersistence the commerce order note persistence
	 */
	public void setCommerceOrderNotePersistence(
		CommerceOrderNotePersistence commerceOrderNotePersistence) {
		this.commerceOrderNotePersistence = commerceOrderNotePersistence;
	}

	/**
	 * Returns the commerce order payment local service.
	 *
	 * @return the commerce order payment local service
	 */
	public com.liferay.commerce.service.CommerceOrderPaymentLocalService getCommerceOrderPaymentLocalService() {
		return commerceOrderPaymentLocalService;
	}

	/**
	 * Sets the commerce order payment local service.
	 *
	 * @param commerceOrderPaymentLocalService the commerce order payment local service
	 */
	public void setCommerceOrderPaymentLocalService(
		com.liferay.commerce.service.CommerceOrderPaymentLocalService commerceOrderPaymentLocalService) {
		this.commerceOrderPaymentLocalService = commerceOrderPaymentLocalService;
	}

	/**
	 * Returns the commerce order payment persistence.
	 *
	 * @return the commerce order payment persistence
	 */
	public CommerceOrderPaymentPersistence getCommerceOrderPaymentPersistence() {
		return commerceOrderPaymentPersistence;
	}

	/**
	 * Sets the commerce order payment persistence.
	 *
	 * @param commerceOrderPaymentPersistence the commerce order payment persistence
	 */
	public void setCommerceOrderPaymentPersistence(
		CommerceOrderPaymentPersistence commerceOrderPaymentPersistence) {
		this.commerceOrderPaymentPersistence = commerceOrderPaymentPersistence;
	}

	/**
	 * Returns the commerce payment method local service.
	 *
	 * @return the commerce payment method local service
	 */
	public com.liferay.commerce.service.CommercePaymentMethodLocalService getCommercePaymentMethodLocalService() {
		return commercePaymentMethodLocalService;
	}

	/**
	 * Sets the commerce payment method local service.
	 *
	 * @param commercePaymentMethodLocalService the commerce payment method local service
	 */
	public void setCommercePaymentMethodLocalService(
		com.liferay.commerce.service.CommercePaymentMethodLocalService commercePaymentMethodLocalService) {
		this.commercePaymentMethodLocalService = commercePaymentMethodLocalService;
	}

	/**
	 * Returns the commerce payment method persistence.
	 *
	 * @return the commerce payment method persistence
	 */
	public CommercePaymentMethodPersistence getCommercePaymentMethodPersistence() {
		return commercePaymentMethodPersistence;
	}

	/**
	 * Sets the commerce payment method persistence.
	 *
	 * @param commercePaymentMethodPersistence the commerce payment method persistence
	 */
	public void setCommercePaymentMethodPersistence(
		CommercePaymentMethodPersistence commercePaymentMethodPersistence) {
		this.commercePaymentMethodPersistence = commercePaymentMethodPersistence;
	}

	/**
	 * Returns the commerce price entry local service.
	 *
	 * @return the commerce price entry local service
	 */
	public com.liferay.commerce.service.CommercePriceEntryLocalService getCommercePriceEntryLocalService() {
		return commercePriceEntryLocalService;
	}

	/**
	 * Sets the commerce price entry local service.
	 *
	 * @param commercePriceEntryLocalService the commerce price entry local service
	 */
	public void setCommercePriceEntryLocalService(
		com.liferay.commerce.service.CommercePriceEntryLocalService commercePriceEntryLocalService) {
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
	public com.liferay.commerce.service.CommercePriceListLocalService getCommercePriceListLocalService() {
		return commercePriceListLocalService;
	}

	/**
	 * Sets the commerce price list local service.
	 *
	 * @param commercePriceListLocalService the commerce price list local service
	 */
	public void setCommercePriceListLocalService(
		com.liferay.commerce.service.CommercePriceListLocalService commercePriceListLocalService) {
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
	 * Returns the commerce price list qualification type rel local service.
	 *
	 * @return the commerce price list qualification type rel local service
	 */
	public com.liferay.commerce.service.CommercePriceListQualificationTypeRelLocalService getCommercePriceListQualificationTypeRelLocalService() {
		return commercePriceListQualificationTypeRelLocalService;
	}

	/**
	 * Sets the commerce price list qualification type rel local service.
	 *
	 * @param commercePriceListQualificationTypeRelLocalService the commerce price list qualification type rel local service
	 */
	public void setCommercePriceListQualificationTypeRelLocalService(
		com.liferay.commerce.service.CommercePriceListQualificationTypeRelLocalService commercePriceListQualificationTypeRelLocalService) {
		this.commercePriceListQualificationTypeRelLocalService = commercePriceListQualificationTypeRelLocalService;
	}

	/**
	 * Returns the commerce price list qualification type rel persistence.
	 *
	 * @return the commerce price list qualification type rel persistence
	 */
	public CommercePriceListQualificationTypeRelPersistence getCommercePriceListQualificationTypeRelPersistence() {
		return commercePriceListQualificationTypeRelPersistence;
	}

	/**
	 * Sets the commerce price list qualification type rel persistence.
	 *
	 * @param commercePriceListQualificationTypeRelPersistence the commerce price list qualification type rel persistence
	 */
	public void setCommercePriceListQualificationTypeRelPersistence(
		CommercePriceListQualificationTypeRelPersistence commercePriceListQualificationTypeRelPersistence) {
		this.commercePriceListQualificationTypeRelPersistence = commercePriceListQualificationTypeRelPersistence;
	}

	/**
	 * Returns the commerce region local service.
	 *
	 * @return the commerce region local service
	 */
	public com.liferay.commerce.service.CommerceRegionLocalService getCommerceRegionLocalService() {
		return commerceRegionLocalService;
	}

	/**
	 * Sets the commerce region local service.
	 *
	 * @param commerceRegionLocalService the commerce region local service
	 */
	public void setCommerceRegionLocalService(
		com.liferay.commerce.service.CommerceRegionLocalService commerceRegionLocalService) {
		this.commerceRegionLocalService = commerceRegionLocalService;
	}

	/**
	 * Returns the commerce region persistence.
	 *
	 * @return the commerce region persistence
	 */
	public CommerceRegionPersistence getCommerceRegionPersistence() {
		return commerceRegionPersistence;
	}

	/**
	 * Sets the commerce region persistence.
	 *
	 * @param commerceRegionPersistence the commerce region persistence
	 */
	public void setCommerceRegionPersistence(
		CommerceRegionPersistence commerceRegionPersistence) {
		this.commerceRegionPersistence = commerceRegionPersistence;
	}

	/**
	 * Returns the commerce shipment local service.
	 *
	 * @return the commerce shipment local service
	 */
	public com.liferay.commerce.service.CommerceShipmentLocalService getCommerceShipmentLocalService() {
		return commerceShipmentLocalService;
	}

	/**
	 * Sets the commerce shipment local service.
	 *
	 * @param commerceShipmentLocalService the commerce shipment local service
	 */
	public void setCommerceShipmentLocalService(
		com.liferay.commerce.service.CommerceShipmentLocalService commerceShipmentLocalService) {
		this.commerceShipmentLocalService = commerceShipmentLocalService;
	}

	/**
	 * Returns the commerce shipment persistence.
	 *
	 * @return the commerce shipment persistence
	 */
	public CommerceShipmentPersistence getCommerceShipmentPersistence() {
		return commerceShipmentPersistence;
	}

	/**
	 * Sets the commerce shipment persistence.
	 *
	 * @param commerceShipmentPersistence the commerce shipment persistence
	 */
	public void setCommerceShipmentPersistence(
		CommerceShipmentPersistence commerceShipmentPersistence) {
		this.commerceShipmentPersistence = commerceShipmentPersistence;
	}

	/**
	 * Returns the commerce shipment item local service.
	 *
	 * @return the commerce shipment item local service
	 */
	public com.liferay.commerce.service.CommerceShipmentItemLocalService getCommerceShipmentItemLocalService() {
		return commerceShipmentItemLocalService;
	}

	/**
	 * Sets the commerce shipment item local service.
	 *
	 * @param commerceShipmentItemLocalService the commerce shipment item local service
	 */
	public void setCommerceShipmentItemLocalService(
		com.liferay.commerce.service.CommerceShipmentItemLocalService commerceShipmentItemLocalService) {
		this.commerceShipmentItemLocalService = commerceShipmentItemLocalService;
	}

	/**
	 * Returns the commerce shipment item persistence.
	 *
	 * @return the commerce shipment item persistence
	 */
	public CommerceShipmentItemPersistence getCommerceShipmentItemPersistence() {
		return commerceShipmentItemPersistence;
	}

	/**
	 * Sets the commerce shipment item persistence.
	 *
	 * @param commerceShipmentItemPersistence the commerce shipment item persistence
	 */
	public void setCommerceShipmentItemPersistence(
		CommerceShipmentItemPersistence commerceShipmentItemPersistence) {
		this.commerceShipmentItemPersistence = commerceShipmentItemPersistence;
	}

	/**
	 * Returns the commerce shipping method local service.
	 *
	 * @return the commerce shipping method local service
	 */
	public com.liferay.commerce.service.CommerceShippingMethodLocalService getCommerceShippingMethodLocalService() {
		return commerceShippingMethodLocalService;
	}

	/**
	 * Sets the commerce shipping method local service.
	 *
	 * @param commerceShippingMethodLocalService the commerce shipping method local service
	 */
	public void setCommerceShippingMethodLocalService(
		com.liferay.commerce.service.CommerceShippingMethodLocalService commerceShippingMethodLocalService) {
		this.commerceShippingMethodLocalService = commerceShippingMethodLocalService;
	}

	/**
	 * Returns the commerce shipping method persistence.
	 *
	 * @return the commerce shipping method persistence
	 */
	public CommerceShippingMethodPersistence getCommerceShippingMethodPersistence() {
		return commerceShippingMethodPersistence;
	}

	/**
	 * Sets the commerce shipping method persistence.
	 *
	 * @param commerceShippingMethodPersistence the commerce shipping method persistence
	 */
	public void setCommerceShippingMethodPersistence(
		CommerceShippingMethodPersistence commerceShippingMethodPersistence) {
		this.commerceShippingMethodPersistence = commerceShippingMethodPersistence;
	}

	/**
	 * Returns the commerce tier price entry local service.
	 *
	 * @return the commerce tier price entry local service
	 */
	public com.liferay.commerce.service.CommerceTierPriceEntryLocalService getCommerceTierPriceEntryLocalService() {
		return commerceTierPriceEntryLocalService;
	}

	/**
	 * Sets the commerce tier price entry local service.
	 *
	 * @param commerceTierPriceEntryLocalService the commerce tier price entry local service
	 */
	public void setCommerceTierPriceEntryLocalService(
		com.liferay.commerce.service.CommerceTierPriceEntryLocalService commerceTierPriceEntryLocalService) {
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
	 * Returns the commerce warehouse local service.
	 *
	 * @return the commerce warehouse local service
	 */
	public com.liferay.commerce.service.CommerceWarehouseLocalService getCommerceWarehouseLocalService() {
		return commerceWarehouseLocalService;
	}

	/**
	 * Sets the commerce warehouse local service.
	 *
	 * @param commerceWarehouseLocalService the commerce warehouse local service
	 */
	public void setCommerceWarehouseLocalService(
		com.liferay.commerce.service.CommerceWarehouseLocalService commerceWarehouseLocalService) {
		this.commerceWarehouseLocalService = commerceWarehouseLocalService;
	}

	/**
	 * Returns the commerce warehouse persistence.
	 *
	 * @return the commerce warehouse persistence
	 */
	public CommerceWarehousePersistence getCommerceWarehousePersistence() {
		return commerceWarehousePersistence;
	}

	/**
	 * Sets the commerce warehouse persistence.
	 *
	 * @param commerceWarehousePersistence the commerce warehouse persistence
	 */
	public void setCommerceWarehousePersistence(
		CommerceWarehousePersistence commerceWarehousePersistence) {
		this.commerceWarehousePersistence = commerceWarehousePersistence;
	}

	/**
	 * Returns the commerce warehouse finder.
	 *
	 * @return the commerce warehouse finder
	 */
	public CommerceWarehouseFinder getCommerceWarehouseFinder() {
		return commerceWarehouseFinder;
	}

	/**
	 * Sets the commerce warehouse finder.
	 *
	 * @param commerceWarehouseFinder the commerce warehouse finder
	 */
	public void setCommerceWarehouseFinder(
		CommerceWarehouseFinder commerceWarehouseFinder) {
		this.commerceWarehouseFinder = commerceWarehouseFinder;
	}

	/**
	 * Returns the commerce warehouse item local service.
	 *
	 * @return the commerce warehouse item local service
	 */
	public com.liferay.commerce.service.CommerceWarehouseItemLocalService getCommerceWarehouseItemLocalService() {
		return commerceWarehouseItemLocalService;
	}

	/**
	 * Sets the commerce warehouse item local service.
	 *
	 * @param commerceWarehouseItemLocalService the commerce warehouse item local service
	 */
	public void setCommerceWarehouseItemLocalService(
		com.liferay.commerce.service.CommerceWarehouseItemLocalService commerceWarehouseItemLocalService) {
		this.commerceWarehouseItemLocalService = commerceWarehouseItemLocalService;
	}

	/**
	 * Returns the commerce warehouse item persistence.
	 *
	 * @return the commerce warehouse item persistence
	 */
	public CommerceWarehouseItemPersistence getCommerceWarehouseItemPersistence() {
		return commerceWarehouseItemPersistence;
	}

	/**
	 * Sets the commerce warehouse item persistence.
	 *
	 * @param commerceWarehouseItemPersistence the commerce warehouse item persistence
	 */
	public void setCommerceWarehouseItemPersistence(
		CommerceWarehouseItemPersistence commerceWarehouseItemPersistence) {
		this.commerceWarehouseItemPersistence = commerceWarehouseItemPersistence;
	}

	/**
	 * Returns the commerce warehouse item finder.
	 *
	 * @return the commerce warehouse item finder
	 */
	public CommerceWarehouseItemFinder getCommerceWarehouseItemFinder() {
		return commerceWarehouseItemFinder;
	}

	/**
	 * Sets the commerce warehouse item finder.
	 *
	 * @param commerceWarehouseItemFinder the commerce warehouse item finder
	 */
	public void setCommerceWarehouseItemFinder(
		CommerceWarehouseItemFinder commerceWarehouseItemFinder) {
		this.commerceWarehouseItemFinder = commerceWarehouseItemFinder;
	}

	/**
	 * Returns the cp definition availability range local service.
	 *
	 * @return the cp definition availability range local service
	 */
	public CPDefinitionAvailabilityRangeLocalService getCPDefinitionAvailabilityRangeLocalService() {
		return cpDefinitionAvailabilityRangeLocalService;
	}

	/**
	 * Sets the cp definition availability range local service.
	 *
	 * @param cpDefinitionAvailabilityRangeLocalService the cp definition availability range local service
	 */
	public void setCPDefinitionAvailabilityRangeLocalService(
		CPDefinitionAvailabilityRangeLocalService cpDefinitionAvailabilityRangeLocalService) {
		this.cpDefinitionAvailabilityRangeLocalService = cpDefinitionAvailabilityRangeLocalService;
	}

	/**
	 * Returns the cp definition availability range persistence.
	 *
	 * @return the cp definition availability range persistence
	 */
	public CPDefinitionAvailabilityRangePersistence getCPDefinitionAvailabilityRangePersistence() {
		return cpDefinitionAvailabilityRangePersistence;
	}

	/**
	 * Sets the cp definition availability range persistence.
	 *
	 * @param cpDefinitionAvailabilityRangePersistence the cp definition availability range persistence
	 */
	public void setCPDefinitionAvailabilityRangePersistence(
		CPDefinitionAvailabilityRangePersistence cpDefinitionAvailabilityRangePersistence) {
		this.cpDefinitionAvailabilityRangePersistence = cpDefinitionAvailabilityRangePersistence;
	}

	/**
	 * Returns the cp definition inventory local service.
	 *
	 * @return the cp definition inventory local service
	 */
	public com.liferay.commerce.service.CPDefinitionInventoryLocalService getCPDefinitionInventoryLocalService() {
		return cpDefinitionInventoryLocalService;
	}

	/**
	 * Sets the cp definition inventory local service.
	 *
	 * @param cpDefinitionInventoryLocalService the cp definition inventory local service
	 */
	public void setCPDefinitionInventoryLocalService(
		com.liferay.commerce.service.CPDefinitionInventoryLocalService cpDefinitionInventoryLocalService) {
		this.cpDefinitionInventoryLocalService = cpDefinitionInventoryLocalService;
	}

	/**
	 * Returns the cp definition inventory persistence.
	 *
	 * @return the cp definition inventory persistence
	 */
	public CPDefinitionInventoryPersistence getCPDefinitionInventoryPersistence() {
		return cpDefinitionInventoryPersistence;
	}

	/**
	 * Sets the cp definition inventory persistence.
	 *
	 * @param cpDefinitionInventoryPersistence the cp definition inventory persistence
	 */
	public void setCPDefinitionInventoryPersistence(
		CPDefinitionInventoryPersistence cpDefinitionInventoryPersistence) {
		this.cpDefinitionInventoryPersistence = cpDefinitionInventoryPersistence;
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
		persistedModelLocalServiceRegistry.register("com.liferay.commerce.model.CPDefinitionAvailabilityRange",
			cpDefinitionAvailabilityRangeLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.model.CPDefinitionAvailabilityRange");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CPDefinitionAvailabilityRangeLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CPDefinitionAvailabilityRange.class;
	}

	protected String getModelClassName() {
		return CPDefinitionAvailabilityRange.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = cpDefinitionAvailabilityRangePersistence.getDataSource();

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

	@BeanReference(type = com.liferay.commerce.service.CommerceAddressLocalService.class)
	protected com.liferay.commerce.service.CommerceAddressLocalService commerceAddressLocalService;
	@BeanReference(type = CommerceAddressPersistence.class)
	protected CommerceAddressPersistence commerceAddressPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceAddressRestrictionLocalService.class)
	protected com.liferay.commerce.service.CommerceAddressRestrictionLocalService commerceAddressRestrictionLocalService;
	@BeanReference(type = CommerceAddressRestrictionPersistence.class)
	protected CommerceAddressRestrictionPersistence commerceAddressRestrictionPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceAvailabilityRangeLocalService.class)
	protected com.liferay.commerce.service.CommerceAvailabilityRangeLocalService commerceAvailabilityRangeLocalService;
	@BeanReference(type = CommerceAvailabilityRangePersistence.class)
	protected CommerceAvailabilityRangePersistence commerceAvailabilityRangePersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceCountryLocalService.class)
	protected com.liferay.commerce.service.CommerceCountryLocalService commerceCountryLocalService;
	@BeanReference(type = CommerceCountryPersistence.class)
	protected CommerceCountryPersistence commerceCountryPersistence;
	@BeanReference(type = CommerceCountryFinder.class)
	protected CommerceCountryFinder commerceCountryFinder;
	@BeanReference(type = com.liferay.commerce.service.CommerceOrderLocalService.class)
	protected com.liferay.commerce.service.CommerceOrderLocalService commerceOrderLocalService;
	@BeanReference(type = CommerceOrderPersistence.class)
	protected CommerceOrderPersistence commerceOrderPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceOrderItemLocalService.class)
	protected com.liferay.commerce.service.CommerceOrderItemLocalService commerceOrderItemLocalService;
	@BeanReference(type = CommerceOrderItemPersistence.class)
	protected CommerceOrderItemPersistence commerceOrderItemPersistence;
	@BeanReference(type = CommerceOrderItemFinder.class)
	protected CommerceOrderItemFinder commerceOrderItemFinder;
	@BeanReference(type = com.liferay.commerce.service.CommerceOrderNoteLocalService.class)
	protected com.liferay.commerce.service.CommerceOrderNoteLocalService commerceOrderNoteLocalService;
	@BeanReference(type = CommerceOrderNotePersistence.class)
	protected CommerceOrderNotePersistence commerceOrderNotePersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceOrderPaymentLocalService.class)
	protected com.liferay.commerce.service.CommerceOrderPaymentLocalService commerceOrderPaymentLocalService;
	@BeanReference(type = CommerceOrderPaymentPersistence.class)
	protected CommerceOrderPaymentPersistence commerceOrderPaymentPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommercePaymentMethodLocalService.class)
	protected com.liferay.commerce.service.CommercePaymentMethodLocalService commercePaymentMethodLocalService;
	@BeanReference(type = CommercePaymentMethodPersistence.class)
	protected CommercePaymentMethodPersistence commercePaymentMethodPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommercePriceEntryLocalService.class)
	protected com.liferay.commerce.service.CommercePriceEntryLocalService commercePriceEntryLocalService;
	@BeanReference(type = CommercePriceEntryPersistence.class)
	protected CommercePriceEntryPersistence commercePriceEntryPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommercePriceListLocalService.class)
	protected com.liferay.commerce.service.CommercePriceListLocalService commercePriceListLocalService;
	@BeanReference(type = CommercePriceListPersistence.class)
	protected CommercePriceListPersistence commercePriceListPersistence;
	@BeanReference(type = CommercePriceListFinder.class)
	protected CommercePriceListFinder commercePriceListFinder;
	@BeanReference(type = com.liferay.commerce.service.CommercePriceListQualificationTypeRelLocalService.class)
	protected com.liferay.commerce.service.CommercePriceListQualificationTypeRelLocalService commercePriceListQualificationTypeRelLocalService;
	@BeanReference(type = CommercePriceListQualificationTypeRelPersistence.class)
	protected CommercePriceListQualificationTypeRelPersistence commercePriceListQualificationTypeRelPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceRegionLocalService.class)
	protected com.liferay.commerce.service.CommerceRegionLocalService commerceRegionLocalService;
	@BeanReference(type = CommerceRegionPersistence.class)
	protected CommerceRegionPersistence commerceRegionPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceShipmentLocalService.class)
	protected com.liferay.commerce.service.CommerceShipmentLocalService commerceShipmentLocalService;
	@BeanReference(type = CommerceShipmentPersistence.class)
	protected CommerceShipmentPersistence commerceShipmentPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceShipmentItemLocalService.class)
	protected com.liferay.commerce.service.CommerceShipmentItemLocalService commerceShipmentItemLocalService;
	@BeanReference(type = CommerceShipmentItemPersistence.class)
	protected CommerceShipmentItemPersistence commerceShipmentItemPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceShippingMethodLocalService.class)
	protected com.liferay.commerce.service.CommerceShippingMethodLocalService commerceShippingMethodLocalService;
	@BeanReference(type = CommerceShippingMethodPersistence.class)
	protected CommerceShippingMethodPersistence commerceShippingMethodPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceTierPriceEntryLocalService.class)
	protected com.liferay.commerce.service.CommerceTierPriceEntryLocalService commerceTierPriceEntryLocalService;
	@BeanReference(type = CommerceTierPriceEntryPersistence.class)
	protected CommerceTierPriceEntryPersistence commerceTierPriceEntryPersistence;
	@BeanReference(type = com.liferay.commerce.service.CommerceWarehouseLocalService.class)
	protected com.liferay.commerce.service.CommerceWarehouseLocalService commerceWarehouseLocalService;
	@BeanReference(type = CommerceWarehousePersistence.class)
	protected CommerceWarehousePersistence commerceWarehousePersistence;
	@BeanReference(type = CommerceWarehouseFinder.class)
	protected CommerceWarehouseFinder commerceWarehouseFinder;
	@BeanReference(type = com.liferay.commerce.service.CommerceWarehouseItemLocalService.class)
	protected com.liferay.commerce.service.CommerceWarehouseItemLocalService commerceWarehouseItemLocalService;
	@BeanReference(type = CommerceWarehouseItemPersistence.class)
	protected CommerceWarehouseItemPersistence commerceWarehouseItemPersistence;
	@BeanReference(type = CommerceWarehouseItemFinder.class)
	protected CommerceWarehouseItemFinder commerceWarehouseItemFinder;
	@BeanReference(type = CPDefinitionAvailabilityRangeLocalService.class)
	protected CPDefinitionAvailabilityRangeLocalService cpDefinitionAvailabilityRangeLocalService;
	@BeanReference(type = CPDefinitionAvailabilityRangePersistence.class)
	protected CPDefinitionAvailabilityRangePersistence cpDefinitionAvailabilityRangePersistence;
	@BeanReference(type = com.liferay.commerce.service.CPDefinitionInventoryLocalService.class)
	protected com.liferay.commerce.service.CPDefinitionInventoryLocalService cpDefinitionInventoryLocalService;
	@BeanReference(type = CPDefinitionInventoryPersistence.class)
	protected CPDefinitionInventoryPersistence cpDefinitionInventoryPersistence;
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