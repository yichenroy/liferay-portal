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

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link AuthenticationTokenService}.
 *
 * @author Brian Wing Shun Chan
 * @see AuthenticationTokenService
 * @generated
 */
@ProviderType
public class AuthenticationTokenServiceWrapper
	implements AuthenticationTokenService,
			   ServiceWrapper<AuthenticationTokenService> {

	public AuthenticationTokenServiceWrapper(
		AuthenticationTokenService authenticationTokenService) {

		_authenticationTokenService = authenticationTokenService;
	}

	@Override
	public com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			addAuthenticationToken(
				long serviceProducerId, String name, String token)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _authenticationTokenService.addAuthenticationToken(
			serviceProducerId, name, token);
	}

	@Override
	public com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			deleteAuthenticationToken(long authenticationTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _authenticationTokenService.deleteAuthenticationToken(
			authenticationTokenId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _authenticationTokenService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			updateAuthenticationToken(long authenticationTokenId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _authenticationTokenService.updateAuthenticationToken(
			authenticationTokenId, name);
	}

	@Override
	public com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			updateStatus(long authenticationTokenId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _authenticationTokenService.updateStatus(
			authenticationTokenId, status);
	}

	@Override
	public AuthenticationTokenService getWrappedService() {
		return _authenticationTokenService;
	}

	@Override
	public void setWrappedService(
		AuthenticationTokenService authenticationTokenService) {

		_authenticationTokenService = authenticationTokenService;
	}

	private AuthenticationTokenService _authenticationTokenService;

}