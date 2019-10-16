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
 * Provides a wrapper for {@link SamlSpIdpConnectionLocalService}.
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnectionLocalService
 * @generated
 */
@ProviderType
public class SamlSpIdpConnectionLocalServiceWrapper
	implements SamlSpIdpConnectionLocalService,
		ServiceWrapper<SamlSpIdpConnectionLocalService> {
	public SamlSpIdpConnectionLocalServiceWrapper(
		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService) {
		_samlSpIdpConnectionLocalService = samlSpIdpConnectionLocalService;
	}

	/**
	* Adds the saml sp idp connection to the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpIdpConnection the saml sp idp connection
	* @return the saml sp idp connection that was added
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpIdpConnection addSamlSpIdpConnection(
		com.liferay.saml.persistence.model.SamlSpIdpConnection samlSpIdpConnection) {
		return _samlSpIdpConnectionLocalService.addSamlSpIdpConnection(samlSpIdpConnection);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpIdpConnection addSamlSpIdpConnection(
		java.lang.String samlIdpEntityId, boolean assertionSignatureRequired,
		long clockSkew, boolean enabled, boolean forceAuthn,
		boolean ldapImportEnabled, java.lang.String metadataUrl,
		java.io.InputStream metadataXmlInputStream, java.lang.String name,
		java.lang.String nameIdFormat, boolean signAuthnRequest,
		java.lang.String userAttributeMappings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpIdpConnectionLocalService.addSamlSpIdpConnection(samlIdpEntityId,
			assertionSignatureRequired, clockSkew, enabled, forceAuthn,
			ldapImportEnabled, metadataUrl, metadataXmlInputStream, name,
			nameIdFormat, signAuthnRequest, userAttributeMappings,
			serviceContext);
	}

	/**
	* Creates a new saml sp idp connection with the primary key. Does not add the saml sp idp connection to the database.
	*
	* @param samlSpIdpConnectionId the primary key for the new saml sp idp connection
	* @return the new saml sp idp connection
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpIdpConnection createSamlSpIdpConnection(
		long samlSpIdpConnectionId) {
		return _samlSpIdpConnectionLocalService.createSamlSpIdpConnection(samlSpIdpConnectionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpIdpConnectionLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the saml sp idp connection from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpIdpConnection the saml sp idp connection
	* @return the saml sp idp connection that was removed
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpIdpConnection deleteSamlSpIdpConnection(
		com.liferay.saml.persistence.model.SamlSpIdpConnection samlSpIdpConnection) {
		return _samlSpIdpConnectionLocalService.deleteSamlSpIdpConnection(samlSpIdpConnection);
	}

	/**
	* Deletes the saml sp idp connection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	* @return the saml sp idp connection that was removed
	* @throws PortalException if a saml sp idp connection with the primary key could not be found
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpIdpConnection deleteSamlSpIdpConnection(
		long samlSpIdpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpIdpConnectionLocalService.deleteSamlSpIdpConnection(samlSpIdpConnectionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _samlSpIdpConnectionLocalService.dynamicQuery();
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
		return _samlSpIdpConnectionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _samlSpIdpConnectionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _samlSpIdpConnectionLocalService.dynamicQuery(dynamicQuery,
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
		return _samlSpIdpConnectionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _samlSpIdpConnectionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpIdpConnection fetchSamlSpIdpConnection(
		long samlSpIdpConnectionId) {
		return _samlSpIdpConnectionLocalService.fetchSamlSpIdpConnection(samlSpIdpConnectionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _samlSpIdpConnectionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _samlSpIdpConnectionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _samlSpIdpConnectionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpIdpConnectionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the saml sp idp connection with the primary key.
	*
	* @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	* @return the saml sp idp connection
	* @throws PortalException if a saml sp idp connection with the primary key could not be found
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpIdpConnection getSamlSpIdpConnection(
		long samlSpIdpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpIdpConnectionLocalService.getSamlSpIdpConnection(samlSpIdpConnectionId);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpIdpConnection getSamlSpIdpConnection(
		long companyId, java.lang.String samlIdpEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpIdpConnectionLocalService.getSamlSpIdpConnection(companyId,
			samlIdpEntityId);
	}

	/**
	* Returns a range of all the saml sp idp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @return the range of saml sp idp connections
	*/
	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlSpIdpConnection> getSamlSpIdpConnections(
		int start, int end) {
		return _samlSpIdpConnectionLocalService.getSamlSpIdpConnections(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlSpIdpConnection> getSamlSpIdpConnections(
		long companyId) {
		return _samlSpIdpConnectionLocalService.getSamlSpIdpConnections(companyId);
	}

	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlSpIdpConnection> getSamlSpIdpConnections(
		long companyId, int start, int end) {
		return _samlSpIdpConnectionLocalService.getSamlSpIdpConnections(companyId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlSpIdpConnection> getSamlSpIdpConnections(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _samlSpIdpConnectionLocalService.getSamlSpIdpConnections(companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of saml sp idp connections.
	*
	* @return the number of saml sp idp connections
	*/
	@Override
	public int getSamlSpIdpConnectionsCount() {
		return _samlSpIdpConnectionLocalService.getSamlSpIdpConnectionsCount();
	}

	@Override
	public int getSamlSpIdpConnectionsCount(long companyId) {
		return _samlSpIdpConnectionLocalService.getSamlSpIdpConnectionsCount(companyId);
	}

	@Override
	public void updateMetadata(long samlSpIdpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_samlSpIdpConnectionLocalService.updateMetadata(samlSpIdpConnectionId);
	}

	/**
	* Updates the saml sp idp connection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param samlSpIdpConnection the saml sp idp connection
	* @return the saml sp idp connection that was updated
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpIdpConnection updateSamlSpIdpConnection(
		com.liferay.saml.persistence.model.SamlSpIdpConnection samlSpIdpConnection) {
		return _samlSpIdpConnectionLocalService.updateSamlSpIdpConnection(samlSpIdpConnection);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpIdpConnection updateSamlSpIdpConnection(
		long samlSpIdpConnectionId, java.lang.String samlIdpEntityId,
		boolean assertionSignatureRequired, long clockSkew, boolean enabled,
		boolean forceAuthn, boolean ldapImportEnabled,
		java.lang.String metadataUrl,
		java.io.InputStream metadataXmlInputStream, java.lang.String name,
		java.lang.String nameIdFormat, boolean signAuthnRequest,
		java.lang.String userAttributeMappings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpIdpConnectionLocalService.updateSamlSpIdpConnection(samlSpIdpConnectionId,
			samlIdpEntityId, assertionSignatureRequired, clockSkew, enabled,
			forceAuthn, ldapImportEnabled, metadataUrl, metadataXmlInputStream,
			name, nameIdFormat, signAuthnRequest, userAttributeMappings,
			serviceContext);
	}

	@Override
	public SamlSpIdpConnectionLocalService getWrappedService() {
		return _samlSpIdpConnectionLocalService;
	}

	@Override
	public void setWrappedService(
		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService) {
		_samlSpIdpConnectionLocalService = samlSpIdpConnectionLocalService;
	}

	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;
}