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
 * Provides a wrapper for {@link SamlIdpSpConnectionLocalService}.
 *
 * @author Mika Koivisto
 * @see SamlIdpSpConnectionLocalService
 * @generated
 */
@ProviderType
public class SamlIdpSpConnectionLocalServiceWrapper
	implements SamlIdpSpConnectionLocalService,
		ServiceWrapper<SamlIdpSpConnectionLocalService> {
	public SamlIdpSpConnectionLocalServiceWrapper(
		SamlIdpSpConnectionLocalService samlIdpSpConnectionLocalService) {
		_samlIdpSpConnectionLocalService = samlIdpSpConnectionLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _samlIdpSpConnectionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _samlIdpSpConnectionLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _samlIdpSpConnectionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSpConnectionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSpConnectionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the saml idp sp connection to the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpConnection the saml idp sp connection
	* @return the saml idp sp connection that was added
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSpConnection addSamlIdpSpConnection(
		com.liferay.saml.persistence.model.SamlIdpSpConnection samlIdpSpConnection) {
		return _samlIdpSpConnectionLocalService.addSamlIdpSpConnection(samlIdpSpConnection);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlIdpSpConnection addSamlIdpSpConnection(
		java.lang.String samlSpEntityId, int assertionLifetime,
		java.lang.String attributeNames, boolean attributesEnabled,
		boolean attributesNamespaceEnabled, boolean enabled,
		java.lang.String metadataUrl,
		java.io.InputStream metadataXmlInputStream, java.lang.String name,
		java.lang.String nameIdAttribute, java.lang.String nameIdFormat,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSpConnectionLocalService.addSamlIdpSpConnection(samlSpEntityId,
			assertionLifetime, attributeNames, attributesEnabled,
			attributesNamespaceEnabled, enabled, metadataUrl,
			metadataXmlInputStream, name, nameIdAttribute, nameIdFormat,
			serviceContext);
	}

	/**
	* Creates a new saml idp sp connection with the primary key. Does not add the saml idp sp connection to the database.
	*
	* @param samlIdpSpConnectionId the primary key for the new saml idp sp connection
	* @return the new saml idp sp connection
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSpConnection createSamlIdpSpConnection(
		long samlIdpSpConnectionId) {
		return _samlIdpSpConnectionLocalService.createSamlIdpSpConnection(samlIdpSpConnectionId);
	}

	/**
	* Deletes the saml idp sp connection from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpConnection the saml idp sp connection
	* @return the saml idp sp connection that was removed
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSpConnection deleteSamlIdpSpConnection(
		com.liferay.saml.persistence.model.SamlIdpSpConnection samlIdpSpConnection) {
		return _samlIdpSpConnectionLocalService.deleteSamlIdpSpConnection(samlIdpSpConnection);
	}

	/**
	* Deletes the saml idp sp connection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	* @return the saml idp sp connection that was removed
	* @throws PortalException if a saml idp sp connection with the primary key could not be found
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSpConnection deleteSamlIdpSpConnection(
		long samlIdpSpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSpConnectionLocalService.deleteSamlIdpSpConnection(samlIdpSpConnectionId);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlIdpSpConnection fetchSamlIdpSpConnection(
		long samlIdpSpConnectionId) {
		return _samlIdpSpConnectionLocalService.fetchSamlIdpSpConnection(samlIdpSpConnectionId);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlIdpSpConnection getSamlIdpSpConnection(
		long companyId, java.lang.String samlSpEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSpConnectionLocalService.getSamlIdpSpConnection(companyId,
			samlSpEntityId);
	}

	/**
	* Returns the saml idp sp connection with the primary key.
	*
	* @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	* @return the saml idp sp connection
	* @throws PortalException if a saml idp sp connection with the primary key could not be found
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSpConnection getSamlIdpSpConnection(
		long samlIdpSpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSpConnectionLocalService.getSamlIdpSpConnection(samlIdpSpConnectionId);
	}

	/**
	* Updates the saml idp sp connection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpConnection the saml idp sp connection
	* @return the saml idp sp connection that was updated
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlIdpSpConnection updateSamlIdpSpConnection(
		com.liferay.saml.persistence.model.SamlIdpSpConnection samlIdpSpConnection) {
		return _samlIdpSpConnectionLocalService.updateSamlIdpSpConnection(samlIdpSpConnection);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlIdpSpConnection updateSamlIdpSpConnection(
		long samlIdpSpConnectionId, java.lang.String samlSpEntityId,
		int assertionLifetime, java.lang.String attributeNames,
		boolean attributesEnabled, boolean attributesNamespaceEnabled,
		boolean enabled, java.lang.String metadataUrl,
		java.io.InputStream metadataXmlInputStream, java.lang.String name,
		java.lang.String nameIdAttribute, java.lang.String nameIdFormat,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlIdpSpConnectionLocalService.updateSamlIdpSpConnection(samlIdpSpConnectionId,
			samlSpEntityId, assertionLifetime, attributeNames,
			attributesEnabled, attributesNamespaceEnabled, enabled,
			metadataUrl, metadataXmlInputStream, name, nameIdAttribute,
			nameIdFormat, serviceContext);
	}

	/**
	* Returns the number of saml idp sp connections.
	*
	* @return the number of saml idp sp connections
	*/
	@Override
	public int getSamlIdpSpConnectionsCount() {
		return _samlIdpSpConnectionLocalService.getSamlIdpSpConnectionsCount();
	}

	@Override
	public int getSamlIdpSpConnectionsCount(long companyId) {
		return _samlIdpSpConnectionLocalService.getSamlIdpSpConnectionsCount(companyId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _samlIdpSpConnectionLocalService.getOSGiServiceIdentifier();
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
		return _samlIdpSpConnectionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _samlIdpSpConnectionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _samlIdpSpConnectionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the saml idp sp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sp connections
	* @param end the upper bound of the range of saml idp sp connections (not inclusive)
	* @return the range of saml idp sp connections
	*/
	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlIdpSpConnection> getSamlIdpSpConnections(
		int start, int end) {
		return _samlIdpSpConnectionLocalService.getSamlIdpSpConnections(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlIdpSpConnection> getSamlIdpSpConnections(
		long companyId) {
		return _samlIdpSpConnectionLocalService.getSamlIdpSpConnections(companyId);
	}

	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlIdpSpConnection> getSamlIdpSpConnections(
		long companyId, int start, int end) {
		return _samlIdpSpConnectionLocalService.getSamlIdpSpConnections(companyId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlIdpSpConnection> getSamlIdpSpConnections(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _samlIdpSpConnectionLocalService.getSamlIdpSpConnections(companyId,
			start, end, orderByComparator);
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
		return _samlIdpSpConnectionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _samlIdpSpConnectionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void updateMetadata(long samlIdpSpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_samlIdpSpConnectionLocalService.updateMetadata(samlIdpSpConnectionId);
	}

	@Override
	public SamlIdpSpConnectionLocalService getWrappedService() {
		return _samlIdpSpConnectionLocalService;
	}

	@Override
	public void setWrappedService(
		SamlIdpSpConnectionLocalService samlIdpSpConnectionLocalService) {
		_samlIdpSpConnectionLocalService = samlIdpSpConnectionLocalService;
	}

	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;
}