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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for CPDefinitionOptionRel. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRelLocalServiceUtil
 * @see com.liferay.commerce.product.service.base.CPDefinitionOptionRelLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionOptionRelLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionOptionRelLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionOptionRelLocalServiceUtil} to access the cp definition option rel local service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionRelLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cp definition option rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionRel the cp definition option rel
	* @return the cp definition option rel that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionOptionRel addCPDefinitionOptionRel(
		CPDefinitionOptionRel cpDefinitionOptionRel);

	public CPDefinitionOptionRel addCPDefinitionOptionRel(long cpDefinitionId,
		long cpOptionId, boolean importOptionValue,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinitionOptionRel addCPDefinitionOptionRel(long cpDefinitionId,
		long cpOptionId, ServiceContext serviceContext)
		throws PortalException;

	public CPDefinitionOptionRel addCPDefinitionOptionRel(long cpDefinitionId,
		long cpOptionId, java.lang.String name,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority, boolean facetable,
		boolean required, boolean skuContributor, boolean importOptionValue,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new cp definition option rel with the primary key. Does not add the cp definition option rel to the database.
	*
	* @param CPDefinitionOptionRelId the primary key for the new cp definition option rel
	* @return the new cp definition option rel
	*/
	public CPDefinitionOptionRel createCPDefinitionOptionRel(
		long CPDefinitionOptionRelId);

	/**
	* Deletes the cp definition option rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionRel the cp definition option rel
	* @return the cp definition option rel that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		CPDefinitionOptionRel cpDefinitionOptionRel) throws PortalException;

	/**
	* Deletes the cp definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	* @return the cp definition option rel that was removed
	* @throws PortalException if a cp definition option rel with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		long CPDefinitionOptionRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionOptionRel fetchCPDefinitionOptionRel(
		long CPDefinitionOptionRelId);

	/**
	* Returns the cp definition option rel matching the UUID and group.
	*
	* @param uuid the cp definition option rel's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionOptionRel fetchCPDefinitionOptionRelByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	/**
	* Returns the cp definition option rel with the primary key.
	*
	* @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	* @return the cp definition option rel
	* @throws PortalException if a cp definition option rel with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionOptionRel getCPDefinitionOptionRel(
		long CPDefinitionOptionRelId) throws PortalException;

	/**
	* Returns the cp definition option rel matching the UUID and group.
	*
	* @param uuid the cp definition option rel's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition option rel
	* @throws PortalException if a matching cp definition option rel could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionOptionRel getCPDefinitionOptionRelByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Updates the cp definition option rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionRel the cp definition option rel
	* @return the cp definition option rel that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionOptionRel updateCPDefinitionOptionRel(
		CPDefinitionOptionRel cpDefinitionOptionRel);

	public CPDefinitionOptionRel updateCPDefinitionOptionRel(
		long cpDefinitionOptionRelId, long cpOptionId, java.lang.String name,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority, boolean facetable,
		boolean required, boolean skuContributor, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPDefinitionOptionRel> searchCPDefinitionOptionRels(
		long companyId, long groupId, long cpDefinitionId,
		java.lang.String keywords, int start, int end, Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	/**
	* Returns the number of cp definition option rels.
	*
	* @return the number of cp definition option rels
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionOptionRelsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSkuContributorCPDefinitionOptionRelCount(long cpDefinitionId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns a range of all the cp definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @return the range of cp definition option rels
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator);

	/**
	* Returns all the cp definition option rels matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition option rels
	* @param companyId the primary key of the company
	* @return the matching cp definition option rels, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of cp definition option rels matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition option rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition option rels, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionRel> getSkuContributorCPDefinitionOptionRels(
		long cpDefinitionId);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void deleteCPDefinitionOptionRels(long cpDefinitionId)
		throws PortalException;
}