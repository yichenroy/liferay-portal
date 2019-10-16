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
 * Provides the local service utility for SamlSpMessage. This utility wraps
 * {@link com.liferay.saml.persistence.service.impl.SamlSpMessageLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Mika Koivisto
 * @see SamlSpMessageLocalService
 * @see com.liferay.saml.persistence.service.base.SamlSpMessageLocalServiceBaseImpl
 * @see com.liferay.saml.persistence.service.impl.SamlSpMessageLocalServiceImpl
 * @generated
 */
@ProviderType
public class SamlSpMessageLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.saml.persistence.service.impl.SamlSpMessageLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
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
	* Adds the saml sp message to the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpMessage the saml sp message
	* @return the saml sp message that was added
	*/
	public static com.liferay.saml.persistence.model.SamlSpMessage addSamlSpMessage(
		com.liferay.saml.persistence.model.SamlSpMessage samlSpMessage) {
		return getService().addSamlSpMessage(samlSpMessage);
	}

	public static com.liferay.saml.persistence.model.SamlSpMessage addSamlSpMessage(
		java.lang.String samlIdpEntityId, java.lang.String samlIdpResponseKey,
		java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return getService()
				   .addSamlSpMessage(samlIdpEntityId, samlIdpResponseKey,
			expirationDate, serviceContext);
	}

	/**
	* Creates a new saml sp message with the primary key. Does not add the saml sp message to the database.
	*
	* @param samlSpMessageId the primary key for the new saml sp message
	* @return the new saml sp message
	*/
	public static com.liferay.saml.persistence.model.SamlSpMessage createSamlSpMessage(
		long samlSpMessageId) {
		return getService().createSamlSpMessage(samlSpMessageId);
	}

	/**
	* Deletes the saml sp message from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpMessage the saml sp message
	* @return the saml sp message that was removed
	*/
	public static com.liferay.saml.persistence.model.SamlSpMessage deleteSamlSpMessage(
		com.liferay.saml.persistence.model.SamlSpMessage samlSpMessage) {
		return getService().deleteSamlSpMessage(samlSpMessage);
	}

	/**
	* Deletes the saml sp message with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpMessageId the primary key of the saml sp message
	* @return the saml sp message that was removed
	* @throws PortalException if a saml sp message with the primary key could not be found
	*/
	public static com.liferay.saml.persistence.model.SamlSpMessage deleteSamlSpMessage(
		long samlSpMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSamlSpMessage(samlSpMessageId);
	}

	public static com.liferay.saml.persistence.model.SamlSpMessage fetchSamlSpMessage(
		java.lang.String samlIdpEntityId, java.lang.String samlIdpResponseKey) {
		return getService()
				   .fetchSamlSpMessage(samlIdpEntityId, samlIdpResponseKey);
	}

	public static com.liferay.saml.persistence.model.SamlSpMessage fetchSamlSpMessage(
		long samlSpMessageId) {
		return getService().fetchSamlSpMessage(samlSpMessageId);
	}

	public static com.liferay.saml.persistence.model.SamlSpMessage getSamlSpMessage(
		java.lang.String samlIdpEntityId, java.lang.String samlIdpResponseKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSamlSpMessage(samlIdpEntityId, samlIdpResponseKey);
	}

	/**
	* Returns the saml sp message with the primary key.
	*
	* @param samlSpMessageId the primary key of the saml sp message
	* @return the saml sp message
	* @throws PortalException if a saml sp message with the primary key could not be found
	*/
	public static com.liferay.saml.persistence.model.SamlSpMessage getSamlSpMessage(
		long samlSpMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSamlSpMessage(samlSpMessageId);
	}

	/**
	* Updates the saml sp message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param samlSpMessage the saml sp message
	* @return the saml sp message that was updated
	*/
	public static com.liferay.saml.persistence.model.SamlSpMessage updateSamlSpMessage(
		com.liferay.saml.persistence.model.SamlSpMessage samlSpMessage) {
		return getService().updateSamlSpMessage(samlSpMessage);
	}

	/**
	* Returns the number of saml sp messages.
	*
	* @return the number of saml sp messages
	*/
	public static int getSamlSpMessagesCount() {
		return getService().getSamlSpMessagesCount();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the saml sp messages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp messages
	* @param end the upper bound of the range of saml sp messages (not inclusive)
	* @return the range of saml sp messages
	*/
	public static java.util.List<com.liferay.saml.persistence.model.SamlSpMessage> getSamlSpMessages(
		int start, int end) {
		return getService().getSamlSpMessages(start, end);
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

	public static void deleteExpiredSamlSpMessages() {
		getService().deleteExpiredSamlSpMessages();
	}

	public static SamlSpMessageLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SamlSpMessageLocalService, SamlSpMessageLocalService> _serviceTracker =
		ServiceTrackerFactory.open(SamlSpMessageLocalService.class);
}