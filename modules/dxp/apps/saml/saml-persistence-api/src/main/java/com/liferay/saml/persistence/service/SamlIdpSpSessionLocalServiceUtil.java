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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SamlIdpSpSession. This utility wraps
 * {@link com.liferay.saml.persistence.service.impl.SamlIdpSpSessionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Mika Koivisto
 * @see SamlIdpSpSessionLocalService
 * @see com.liferay.saml.persistence.service.base.SamlIdpSpSessionLocalServiceBaseImpl
 * @see com.liferay.saml.persistence.service.impl.SamlIdpSpSessionLocalServiceImpl
 * @generated
 */
@ProviderType
public class SamlIdpSpSessionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.saml.persistence.service.impl.SamlIdpSpSessionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the saml idp sp session to the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpSession the saml idp sp session
	* @return the saml idp sp session that was added
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSpSession addSamlIdpSpSession(
		com.liferay.saml.persistence.model.SamlIdpSpSession samlIdpSpSession) {
		return getService().addSamlIdpSpSession(samlIdpSpSession);
	}

	public static com.liferay.saml.persistence.model.SamlIdpSpSession addSamlIdpSpSession(
		long samlIdpSsoSessionId, java.lang.String samlSpEntityId,
		java.lang.String nameIdFormat, java.lang.String nameIdValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSamlIdpSpSession(samlIdpSsoSessionId, samlSpEntityId,
			nameIdFormat, nameIdValue, serviceContext);
	}

	/**
	* Creates a new saml idp sp session with the primary key. Does not add the saml idp sp session to the database.
	*
	* @param samlIdpSpSessionId the primary key for the new saml idp sp session
	* @return the new saml idp sp session
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSpSession createSamlIdpSpSession(
		long samlIdpSpSessionId) {
		return getService().createSamlIdpSpSession(samlIdpSpSessionId);
	}

	/**
	* Deletes the saml idp sp session from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpSession the saml idp sp session
	* @return the saml idp sp session that was removed
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSpSession deleteSamlIdpSpSession(
		com.liferay.saml.persistence.model.SamlIdpSpSession samlIdpSpSession) {
		return getService().deleteSamlIdpSpSession(samlIdpSpSession);
	}

	/**
	* Deletes the saml idp sp session with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpSessionId the primary key of the saml idp sp session
	* @return the saml idp sp session that was removed
	* @throws PortalException if a saml idp sp session with the primary key could not be found
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSpSession deleteSamlIdpSpSession(
		long samlIdpSpSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSamlIdpSpSession(samlIdpSpSessionId);
	}

	public static com.liferay.saml.persistence.model.SamlIdpSpSession fetchSamlIdpSpSession(
		long samlIdpSpSessionId) {
		return getService().fetchSamlIdpSpSession(samlIdpSpSessionId);
	}

	/**
	* Returns the saml idp sp session with the primary key.
	*
	* @param samlIdpSpSessionId the primary key of the saml idp sp session
	* @return the saml idp sp session
	* @throws PortalException if a saml idp sp session with the primary key could not be found
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSpSession getSamlIdpSpSession(
		long samlIdpSpSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSamlIdpSpSession(samlIdpSpSessionId);
	}

	public static com.liferay.saml.persistence.model.SamlIdpSpSession getSamlIdpSpSession(
		long samlIdpSsoSessionId, java.lang.String samlSpEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getSamlIdpSpSession(samlIdpSsoSessionId, samlSpEntityId);
	}

	public static com.liferay.saml.persistence.model.SamlIdpSpSession updateModifiedDate(
		long samlIdpSsoSessionId, java.lang.String samlSpEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateModifiedDate(samlIdpSsoSessionId, samlSpEntityId);
	}

	/**
	* Updates the saml idp sp session in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpSession the saml idp sp session
	* @return the saml idp sp session that was updated
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSpSession updateSamlIdpSpSession(
		com.liferay.saml.persistence.model.SamlIdpSpSession samlIdpSpSession) {
		return getService().updateSamlIdpSpSession(samlIdpSpSession);
	}

	/**
	* Returns the number of saml idp sp sessions.
	*
	* @return the number of saml idp sp sessions
	*/
	public static int getSamlIdpSpSessionsCount() {
		return getService().getSamlIdpSpSessionsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSpSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSpSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns a range of all the saml idp sp sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSpSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sp sessions
	* @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	* @return the range of saml idp sp sessions
	*/
	public static java.util.List<com.liferay.saml.persistence.model.SamlIdpSpSession> getSamlIdpSpSessions(
		int start, int end) {
		return getService().getSamlIdpSpSessions(start, end);
	}

	public static java.util.List<com.liferay.saml.persistence.model.SamlIdpSpSession> getSamlIdpSpSessions(
		long samlIdpSsoSessionId) {
		return getService().getSamlIdpSpSessions(samlIdpSsoSessionId);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static SamlIdpSpSessionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SamlIdpSpSessionLocalService, SamlIdpSpSessionLocalService> _serviceTracker =
		ServiceTrackerFactory.open(SamlIdpSpSessionLocalService.class);
}