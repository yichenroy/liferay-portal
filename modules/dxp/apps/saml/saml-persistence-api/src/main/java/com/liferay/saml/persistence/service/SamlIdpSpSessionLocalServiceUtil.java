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

package com.liferay.saml.persistence.service;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SamlIdpSpSession. This utility wraps
 * <code>com.liferay.saml.persistence.service.impl.SamlIdpSpSessionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Mika Koivisto
 * @see SamlIdpSpSessionLocalService
 * @generated
 */
@ProviderType
public class SamlIdpSpSessionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.saml.persistence.service.impl.SamlIdpSpSessionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpSession
			addSamlIdpSpSession(
				long samlIdpSsoSessionId, String samlSpEntityId,
				String nameIdFormat, String nameIdValue,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSamlIdpSpSession(
			samlIdpSsoSessionId, samlSpEntityId, nameIdFormat, nameIdValue,
			serviceContext);
	}

	/**
	 * Adds the saml idp sp session to the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpSession the saml idp sp session
	 * @return the saml idp sp session that was added
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpSession
		addSamlIdpSpSession(
			com.liferay.saml.persistence.model.SamlIdpSpSession
				samlIdpSpSession) {

		return getService().addSamlIdpSpSession(samlIdpSpSession);
	}

	/**
	 * Creates a new saml idp sp session with the primary key. Does not add the saml idp sp session to the database.
	 *
	 * @param samlIdpSpSessionId the primary key for the new saml idp sp session
	 * @return the new saml idp sp session
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpSession
		createSamlIdpSpSession(long samlIdpSpSessionId) {

		return getService().createSamlIdpSpSession(samlIdpSpSessionId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the saml idp sp session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpSessionId the primary key of the saml idp sp session
	 * @return the saml idp sp session that was removed
	 * @throws PortalException if a saml idp sp session with the primary key could not be found
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpSession
			deleteSamlIdpSpSession(long samlIdpSpSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSamlIdpSpSession(samlIdpSpSessionId);
	}

	/**
	 * Deletes the saml idp sp session from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpSession the saml idp sp session
	 * @return the saml idp sp session that was removed
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpSession
		deleteSamlIdpSpSession(
			com.liferay.saml.persistence.model.SamlIdpSpSession
				samlIdpSpSession) {

		return getService().deleteSamlIdpSpSession(samlIdpSpSession);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlIdpSpSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlIdpSpSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.saml.persistence.model.SamlIdpSpSession
		fetchSamlIdpSpSession(long samlIdpSpSessionId) {

		return getService().fetchSamlIdpSpSession(samlIdpSpSessionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the saml idp sp session with the primary key.
	 *
	 * @param samlIdpSpSessionId the primary key of the saml idp sp session
	 * @return the saml idp sp session
	 * @throws PortalException if a saml idp sp session with the primary key could not be found
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpSession
			getSamlIdpSpSession(long samlIdpSpSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSamlIdpSpSession(samlIdpSpSessionId);
	}

	public static com.liferay.saml.persistence.model.SamlIdpSpSession
			getSamlIdpSpSession(long samlIdpSsoSessionId, String samlSpEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSamlIdpSpSession(
			samlIdpSsoSessionId, samlSpEntityId);
	}

	/**
	 * Returns a range of all the saml idp sp sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlIdpSpSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp sessions
	 * @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	 * @return the range of saml idp sp sessions
	 */
	public static java.util.List
		<com.liferay.saml.persistence.model.SamlIdpSpSession>
			getSamlIdpSpSessions(int start, int end) {

		return getService().getSamlIdpSpSessions(start, end);
	}

	public static java.util.List
		<com.liferay.saml.persistence.model.SamlIdpSpSession>
			getSamlIdpSpSessions(long samlIdpSsoSessionId) {

		return getService().getSamlIdpSpSessions(samlIdpSsoSessionId);
	}

	/**
	 * Returns the number of saml idp sp sessions.
	 *
	 * @return the number of saml idp sp sessions
	 */
	public static int getSamlIdpSpSessionsCount() {
		return getService().getSamlIdpSpSessionsCount();
	}

	public static com.liferay.saml.persistence.model.SamlIdpSpSession
			updateModifiedDate(long samlIdpSsoSessionId, String samlSpEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateModifiedDate(
			samlIdpSsoSessionId, samlSpEntityId);
	}

	/**
	 * Updates the saml idp sp session in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpSession the saml idp sp session
	 * @return the saml idp sp session that was updated
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpSession
		updateSamlIdpSpSession(
			com.liferay.saml.persistence.model.SamlIdpSpSession
				samlIdpSpSession) {

		return getService().updateSamlIdpSpSession(samlIdpSpSession);
	}

	public static SamlIdpSpSessionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SamlIdpSpSessionLocalService, SamlIdpSpSessionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SamlIdpSpSessionLocalService.class);

		ServiceTracker
			<SamlIdpSpSessionLocalService, SamlIdpSpSessionLocalService>
				serviceTracker =
					new ServiceTracker
						<SamlIdpSpSessionLocalService,
						 SamlIdpSpSessionLocalService>(
							 bundle.getBundleContext(),
							 SamlIdpSpSessionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}