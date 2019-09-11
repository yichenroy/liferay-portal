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
 * Provides the remote service utility for AuthenticationToken. This utility wraps
 * <code>com.liferay.osb.koroneiki.scion.service.impl.AuthenticationTokenServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AuthenticationTokenService
 * @generated
 */
@ProviderType
public class AuthenticationTokenServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.scion.service.impl.AuthenticationTokenServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			addAuthenticationToken(
				long serviceProducerId, String name, String token)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAuthenticationToken(
			serviceProducerId, name, token);
	}

	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			deleteAuthenticationToken(long authenticationTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAuthenticationToken(authenticationTokenId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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

	public static AuthenticationTokenService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AuthenticationTokenService, AuthenticationTokenService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AuthenticationTokenService.class);

		ServiceTracker<AuthenticationTokenService, AuthenticationTokenService>
			serviceTracker =
				new ServiceTracker
					<AuthenticationTokenService, AuthenticationTokenService>(
						bundle.getBundleContext(),
						AuthenticationTokenService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}