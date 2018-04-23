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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPRuleLocalService}.
 *
 * @author Marco Leo
 * @see CPRuleLocalService
 * @generated
 */
@ProviderType
public class CPRuleLocalServiceWrapper implements CPRuleLocalService,
	ServiceWrapper<CPRuleLocalService> {
	public CPRuleLocalServiceWrapper(CPRuleLocalService cpRuleLocalService) {
		_cpRuleLocalService = cpRuleLocalService;
	}

	/**
	* Adds the cp rule to the database. Also notifies the appropriate model listeners.
	*
	* @param cpRule the cp rule
	* @return the cp rule that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPRule addCPRule(
		com.liferay.commerce.product.model.CPRule cpRule) {
		return _cpRuleLocalService.addCPRule(cpRule);
	}

	@Override
	public com.liferay.commerce.product.model.CPRule addCPRule(
		java.lang.String name, boolean active, java.lang.String type,
		java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleLocalService.addCPRule(name, active, type, typeSettings,
			serviceContext);
	}

	/**
	* Creates a new cp rule with the primary key. Does not add the cp rule to the database.
	*
	* @param CPRuleId the primary key for the new cp rule
	* @return the new cp rule
	*/
	@Override
	public com.liferay.commerce.product.model.CPRule createCPRule(long CPRuleId) {
		return _cpRuleLocalService.createCPRule(CPRuleId);
	}

	/**
	* Deletes the cp rule from the database. Also notifies the appropriate model listeners.
	*
	* @param cpRule the cp rule
	* @return the cp rule that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPRule deleteCPRule(
		com.liferay.commerce.product.model.CPRule cpRule)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleLocalService.deleteCPRule(cpRule);
	}

	/**
	* Deletes the cp rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPRuleId the primary key of the cp rule
	* @return the cp rule that was removed
	* @throws PortalException if a cp rule with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPRule deleteCPRule(long CPRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleLocalService.deleteCPRule(CPRuleId);
	}

	@Override
	public void deleteCPRules(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpRuleLocalService.deleteCPRules(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpRuleLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _cpRuleLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _cpRuleLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _cpRuleLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _cpRuleLocalService.dynamicQueryCount(dynamicQuery);
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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _cpRuleLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPRule fetchCPRule(long CPRuleId) {
		return _cpRuleLocalService.fetchCPRule(CPRuleId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpRuleLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cp rule with the primary key.
	*
	* @param CPRuleId the primary key of the cp rule
	* @return the cp rule
	* @throws PortalException if a cp rule with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPRule getCPRule(long CPRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleLocalService.getCPRule(CPRuleId);
	}

	/**
	* Returns a range of all the cp rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rules
	* @param end the upper bound of the range of cp rules (not inclusive)
	* @return the range of cp rules
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPRule> getCPRules(
		int start, int end) {
		return _cpRuleLocalService.getCPRules(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPRule> getCPRules(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPRule> orderByComparator) {
		return _cpRuleLocalService.getCPRules(groupId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of cp rules.
	*
	* @return the number of cp rules
	*/
	@Override
	public int getCPRulesCount() {
		return _cpRuleLocalService.getCPRulesCount();
	}

	@Override
	public int getCPRulesCount(long groupId) {
		return _cpRuleLocalService.getCPRulesCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpRuleLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpRuleLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPRule> searchCPRules(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleLocalService.searchCPRules(companyId, groupId, keywords,
			start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPRule> searchCPRules(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleLocalService.searchCPRules(searchContext);
	}

	/**
	* Updates the cp rule in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpRule the cp rule
	* @return the cp rule that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPRule updateCPRule(
		com.liferay.commerce.product.model.CPRule cpRule) {
		return _cpRuleLocalService.updateCPRule(cpRule);
	}

	@Override
	public com.liferay.commerce.product.model.CPRule updateCPRule(
		long cpRuleId, java.lang.String name, boolean active,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleLocalService.updateCPRule(cpRuleId, name, active, type,
			typeSettings, serviceContext);
	}

	@Override
	public CPRuleLocalService getWrappedService() {
		return _cpRuleLocalService;
	}

	@Override
	public void setWrappedService(CPRuleLocalService cpRuleLocalService) {
		_cpRuleLocalService = cpRuleLocalService;
	}

	private CPRuleLocalService _cpRuleLocalService;
}