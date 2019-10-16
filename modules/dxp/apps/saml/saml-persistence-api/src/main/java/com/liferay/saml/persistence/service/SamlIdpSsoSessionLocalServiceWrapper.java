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

package com.liferay.saml.persistence.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SamlIdpSsoSessionLocalService}.
 *
 * @author Mika Koivisto
 * @see SamlIdpSsoSessionLocalService
 * @generated
 */
@ProviderType
public class SamlIdpSsoSessionLocalServiceWrapper
	implements SamlIdpSsoSessionLocalService,
		ServiceWrapper<SamlIdpSsoSessionLocalService> {
	public SamlIdpSsoSessionLocalServiceWrapper(
		SamlIdpSsoSessionLocalService samlIdpSsoSessionLocalService) {
		_samlIdpSsoSessionLocalService = samlIdpSsoSessionLocalService;
	}

	/**
	* Adds the saml idp sso session to the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSsoSession the saml idp sso session
	* @return the saml idp sso session that was added
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession addSamlIdpSsoSession(
		com.liferay.saml.persistence.model.SamlIdpSsoSession samlIdpSsoSession) {
		return _samlIdpSsoSessionLocalService.addSamlIdpSsoSession(samlIdpSsoSession);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession addSamlIdpSsoSession(
		java.lang.String samlIdpSsoSessionKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSsoSessionLocalService.addSamlIdpSsoSession(samlIdpSsoSessionKey,
			serviceContext);
	}

	/**
	* Creates a new saml idp sso session with the primary key. Does not add the saml idp sso session to the database.
	*
	* @param samlIdpSsoSessionId the primary key for the new saml idp sso session
	* @return the new saml idp sso session
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession createSamlIdpSsoSession(
		long samlIdpSsoSessionId) {
		return _samlIdpSsoSessionLocalService.createSamlIdpSsoSession(samlIdpSsoSessionId);
	}

	@Override
	public void deleteExpiredSamlIdpSsoSessions() {
		_samlIdpSsoSessionLocalService.deleteExpiredSamlIdpSsoSessions();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSsoSessionLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the saml idp sso session from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSsoSession the saml idp sso session
	* @return the saml idp sso session that was removed
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession deleteSamlIdpSsoSession(
		com.liferay.saml.persistence.model.SamlIdpSsoSession samlIdpSsoSession) {
		return _samlIdpSsoSessionLocalService.deleteSamlIdpSsoSession(samlIdpSsoSession);
	}

	/**
	* Deletes the saml idp sso session with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSsoSessionId the primary key of the saml idp sso session
	* @return the saml idp sso session that was removed
	* @throws PortalException if a saml idp sso session with the primary key could not be found
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession deleteSamlIdpSsoSession(
		long samlIdpSsoSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSsoSessionLocalService.deleteSamlIdpSsoSession(samlIdpSsoSessionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _samlIdpSsoSessionLocalService.dynamicQuery();
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
		return _samlIdpSsoSessionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _samlIdpSsoSessionLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _samlIdpSsoSessionLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _samlIdpSsoSessionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _samlIdpSsoSessionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession fetchSamlIdpSso(
		java.lang.String samlIdpSsoSessionKey) {
		return _samlIdpSsoSessionLocalService.fetchSamlIdpSso(samlIdpSsoSessionKey);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession fetchSamlIdpSsoSession(
		long samlIdpSsoSessionId) {
		return _samlIdpSsoSessionLocalService.fetchSamlIdpSsoSession(samlIdpSsoSessionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _samlIdpSsoSessionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _samlIdpSsoSessionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _samlIdpSsoSessionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSsoSessionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession getSamlIdpSso(
		java.lang.String samlIdpSsoSessionKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSsoSessionLocalService.getSamlIdpSso(samlIdpSsoSessionKey);
	}

	/**
	* Returns the saml idp sso session with the primary key.
	*
	* @param samlIdpSsoSessionId the primary key of the saml idp sso session
	* @return the saml idp sso session
	* @throws PortalException if a saml idp sso session with the primary key could not be found
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession getSamlIdpSsoSession(
		long samlIdpSsoSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSsoSessionLocalService.getSamlIdpSsoSession(samlIdpSsoSessionId);
	}

	/**
	* Returns a range of all the saml idp sso sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @return the range of saml idp sso sessions
	*/
	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlIdpSsoSession> getSamlIdpSsoSessions(
		int start, int end) {
		return _samlIdpSsoSessionLocalService.getSamlIdpSsoSessions(start, end);
	}

	/**
	* Returns the number of saml idp sso sessions.
	*
	* @return the number of saml idp sso sessions
	*/
	@Override
	public int getSamlIdpSsoSessionsCount() {
		return _samlIdpSsoSessionLocalService.getSamlIdpSsoSessionsCount();
	}

	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession updateModifiedDate(
		java.lang.String samlIdpSsoSessionKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSsoSessionLocalService.updateModifiedDate(samlIdpSsoSessionKey);
	}

	/**
	* Updates the saml idp sso session in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSsoSession the saml idp sso session
	* @return the saml idp sso session that was updated
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSsoSession updateSamlIdpSsoSession(
		com.liferay.saml.persistence.model.SamlIdpSsoSession samlIdpSsoSession) {
		return _samlIdpSsoSessionLocalService.updateSamlIdpSsoSession(samlIdpSsoSession);
	}

	@Override
	public SamlIdpSsoSessionLocalService getWrappedService() {
		return _samlIdpSsoSessionLocalService;
	}

	@Override
	public void setWrappedService(
		SamlIdpSsoSessionLocalService samlIdpSsoSessionLocalService) {
		_samlIdpSsoSessionLocalService = samlIdpSsoSessionLocalService;
	}

	private SamlIdpSsoSessionLocalService _samlIdpSsoSessionLocalService;
}