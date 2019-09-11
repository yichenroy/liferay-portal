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

package com.liferay.osb.koroneiki.scion.service;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AuthenticationToken. This utility wraps
 * <code>com.liferay.osb.koroneiki.scion.service.impl.AuthenticationTokenLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AuthenticationTokenLocalService
 * @generated
 */
@ProviderType
public class AuthenticationTokenLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.scion.service.impl.AuthenticationTokenLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the authentication token to the database. Also notifies the appropriate model listeners.
	 *
	 * @param authenticationToken the authentication token
	 * @return the authentication token that was added
	 */
	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
		addAuthenticationToken(
			com.liferay.osb.koroneiki.scion.model.AuthenticationToken
				authenticationToken) {

		return getService().addAuthenticationToken(authenticationToken);
	}

	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			addAuthenticationToken(
				long userId, long serviceProducerId, String name, String token)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAuthenticationToken(
			userId, serviceProducerId, name, token);
	}

	/**
	 * Creates a new authentication token with the primary key. Does not add the authentication token to the database.
	 *
	 * @param authenticationTokenId the primary key for the new authentication token
	 * @return the new authentication token
	 */
	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
		createAuthenticationToken(long authenticationTokenId) {

		return getService().createAuthenticationToken(authenticationTokenId);
	}

	/**
	 * Deletes the authentication token from the database. Also notifies the appropriate model listeners.
	 *
	 * @param authenticationToken the authentication token
	 * @return the authentication token that was removed
	 */
	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
		deleteAuthenticationToken(
			com.liferay.osb.koroneiki.scion.model.AuthenticationToken
				authenticationToken) {

		return getService().deleteAuthenticationToken(authenticationToken);
	}

	/**
	 * Deletes the authentication token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param authenticationTokenId the primary key of the authentication token
	 * @return the authentication token that was removed
	 * @throws PortalException if a authentication token with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			deleteAuthenticationToken(long authenticationTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAuthenticationToken(authenticationTokenId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.scion.model.impl.AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.scion.model.impl.AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
		fetchAuthenticationToken(long authenticationTokenId) {

		return getService().fetchAuthenticationToken(authenticationTokenId);
	}

	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
		fetchAuthenticationToken(String digest, int status) {

		return getService().fetchAuthenticationToken(digest, status);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the authentication token with the primary key.
	 *
	 * @param authenticationTokenId the primary key of the authentication token
	 * @return the authentication token
	 * @throws PortalException if a authentication token with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			getAuthenticationToken(long authenticationTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAuthenticationToken(authenticationTokenId);
	}

	/**
	 * Returns a range of all the authentication tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.scion.model.impl.AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of authentication tokens
	 * @param end the upper bound of the range of authentication tokens (not inclusive)
	 * @return the range of authentication tokens
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.scion.model.AuthenticationToken>
			getAuthenticationTokens(int start, int end) {

		return getService().getAuthenticationTokens(start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.scion.model.AuthenticationToken>
			getAuthenticationTokens(
				long serviceProducerId, int start, int end) {

		return getService().getAuthenticationTokens(
			serviceProducerId, start, end);
	}

	/**
	 * Returns the number of authentication tokens.
	 *
	 * @return the number of authentication tokens
	 */
	public static int getAuthenticationTokensCount() {
		return getService().getAuthenticationTokensCount();
	}

	public static int getAuthenticationTokensCount(long serviceProducerId) {
		return getService().getAuthenticationTokensCount(serviceProducerId);
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
	 * Updates the authentication token in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param authenticationToken the authentication token
	 * @return the authentication token that was updated
	 */
	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
		updateAuthenticationToken(
			com.liferay.osb.koroneiki.scion.model.AuthenticationToken
				authenticationToken) {

		return getService().updateAuthenticationToken(authenticationToken);
	}

	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			updateAuthenticationToken(long authenticationTokenId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAuthenticationToken(
			authenticationTokenId, name);
	}

	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			updateStatus(long authenticationTokenId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(authenticationTokenId, status);
	}

	public static AuthenticationTokenLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AuthenticationTokenLocalService, AuthenticationTokenLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AuthenticationTokenLocalService.class);

		ServiceTracker
			<AuthenticationTokenLocalService, AuthenticationTokenLocalService>
				serviceTracker =
					new ServiceTracker
						<AuthenticationTokenLocalService,
						 AuthenticationTokenLocalService>(
							 bundle.getBundleContext(),
							 AuthenticationTokenLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}