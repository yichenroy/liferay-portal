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
 * Provides the local service utility for SamlSpAuthRequest. This utility wraps
 * {@link com.liferay.saml.persistence.service.impl.SamlSpAuthRequestLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Mika Koivisto
 * @see SamlSpAuthRequestLocalService
 * @see com.liferay.saml.persistence.service.base.SamlSpAuthRequestLocalServiceBaseImpl
 * @see com.liferay.saml.persistence.service.impl.SamlSpAuthRequestLocalServiceImpl
 * @generated
 */
@ProviderType
public class SamlSpAuthRequestLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.saml.persistence.service.impl.SamlSpAuthRequestLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
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
	* Adds the saml sp auth request to the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpAuthRequest the saml sp auth request
	* @return the saml sp auth request that was added
	*/
	public static com.liferay.saml.persistence.model.SamlSpAuthRequest addSamlSpAuthRequest(
		com.liferay.saml.persistence.model.SamlSpAuthRequest samlSpAuthRequest) {
		return getService().addSamlSpAuthRequest(samlSpAuthRequest);
	}

	public static com.liferay.saml.persistence.model.SamlSpAuthRequest addSamlSpAuthRequest(
		java.lang.String samlIdpEntityId,
		java.lang.String samlSpAuthRequestKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return getService()
				   .addSamlSpAuthRequest(samlIdpEntityId, samlSpAuthRequestKey,
			serviceContext);
	}

	/**
	* Creates a new saml sp auth request with the primary key. Does not add the saml sp auth request to the database.
	*
	* @param samlSpAuthnRequestId the primary key for the new saml sp auth request
	* @return the new saml sp auth request
	*/
	public static com.liferay.saml.persistence.model.SamlSpAuthRequest createSamlSpAuthRequest(
		long samlSpAuthnRequestId) {
		return getService().createSamlSpAuthRequest(samlSpAuthnRequestId);
	}

	/**
	* Deletes the saml sp auth request from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpAuthRequest the saml sp auth request
	* @return the saml sp auth request that was removed
	*/
	public static com.liferay.saml.persistence.model.SamlSpAuthRequest deleteSamlSpAuthRequest(
		com.liferay.saml.persistence.model.SamlSpAuthRequest samlSpAuthRequest) {
		return getService().deleteSamlSpAuthRequest(samlSpAuthRequest);
	}

	/**
	* Deletes the saml sp auth request with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpAuthnRequestId the primary key of the saml sp auth request
	* @return the saml sp auth request that was removed
	* @throws PortalException if a saml sp auth request with the primary key could not be found
	*/
	public static com.liferay.saml.persistence.model.SamlSpAuthRequest deleteSamlSpAuthRequest(
		long samlSpAuthnRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSamlSpAuthRequest(samlSpAuthnRequestId);
	}

	public static com.liferay.saml.persistence.model.SamlSpAuthRequest fetchSamlSpAuthRequest(
		java.lang.String samlIdpEntityId, java.lang.String samlSpAuthRequestKey) {
		return getService()
				   .fetchSamlSpAuthRequest(samlIdpEntityId, samlSpAuthRequestKey);
	}

	public static com.liferay.saml.persistence.model.SamlSpAuthRequest fetchSamlSpAuthRequest(
		long samlSpAuthnRequestId) {
		return getService().fetchSamlSpAuthRequest(samlSpAuthnRequestId);
	}

	public static com.liferay.saml.persistence.model.SamlSpAuthRequest getSamlSpAuthRequest(
		java.lang.String samlIdpEntityId, java.lang.String samlSpAuthRequestKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getSamlSpAuthRequest(samlIdpEntityId, samlSpAuthRequestKey);
	}

	/**
	* Returns the saml sp auth request with the primary key.
	*
	* @param samlSpAuthnRequestId the primary key of the saml sp auth request
	* @return the saml sp auth request
	* @throws PortalException if a saml sp auth request with the primary key could not be found
	*/
	public static com.liferay.saml.persistence.model.SamlSpAuthRequest getSamlSpAuthRequest(
		long samlSpAuthnRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSamlSpAuthRequest(samlSpAuthnRequestId);
	}

	/**
	* Updates the saml sp auth request in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param samlSpAuthRequest the saml sp auth request
	* @return the saml sp auth request that was updated
	*/
	public static com.liferay.saml.persistence.model.SamlSpAuthRequest updateSamlSpAuthRequest(
		com.liferay.saml.persistence.model.SamlSpAuthRequest samlSpAuthRequest) {
		return getService().updateSamlSpAuthRequest(samlSpAuthRequest);
	}

	/**
	* Returns the number of saml sp auth requests.
	*
	* @return the number of saml sp auth requests
	*/
	public static int getSamlSpAuthRequestsCount() {
		return getService().getSamlSpAuthRequestsCount();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpAuthRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpAuthRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the saml sp auth requests.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpAuthRequestModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp auth requests
	* @param end the upper bound of the range of saml sp auth requests (not inclusive)
	* @return the range of saml sp auth requests
	*/
	public static java.util.List<com.liferay.saml.persistence.model.SamlSpAuthRequest> getSamlSpAuthRequests(
		int start, int end) {
		return getService().getSamlSpAuthRequests(start, end);
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

	public static void deleteExpiredSamlSpAuthRequests() {
		getService().deleteExpiredSamlSpAuthRequests();
	}

	public static SamlSpAuthRequestLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SamlSpAuthRequestLocalService, SamlSpAuthRequestLocalService> _serviceTracker =
		ServiceTrackerFactory.open(SamlSpAuthRequestLocalService.class);
}