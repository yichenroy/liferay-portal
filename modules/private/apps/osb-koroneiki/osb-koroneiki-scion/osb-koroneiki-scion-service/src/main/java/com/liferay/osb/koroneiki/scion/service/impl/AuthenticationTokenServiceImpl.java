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

package com.liferay.osb.koroneiki.scion.service.impl;

import com.liferay.osb.koroneiki.scion.constants.ScionActionKeys;
import com.liferay.osb.koroneiki.scion.model.AuthenticationToken;
import com.liferay.osb.koroneiki.scion.permission.AuthenticationTokenPermission;
import com.liferay.osb.koroneiki.scion.service.base.AuthenticationTokenServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
@Component(
	property = {
		"json.web.service.context.name=koroneiki",
		"json.web.service.context.path=AuthenticationToken"
	},
	service = AopService.class
)
public class AuthenticationTokenServiceImpl
	extends AuthenticationTokenServiceBaseImpl {

	public AuthenticationToken addAuthenticationToken(
			long serviceProducerId, String name, String token)
		throws PortalException {

		_authenticationTokenPermission.check(
			getPermissionChecker(), ScionActionKeys.ADD_AUTHENTICATION_TOKEN);

		return authenticationTokenLocalService.addAuthenticationToken(
			getUserId(), serviceProducerId, name, token);
	}

	public AuthenticationToken deleteAuthenticationToken(
			long authenticationTokenId)
		throws PortalException {

		_authenticationTokenPermission.check(
			getPermissionChecker(), authenticationTokenId, ActionKeys.DELETE);

		return authenticationTokenLocalService.deleteAuthenticationToken(
			authenticationTokenId);
	}

	public AuthenticationToken updateAuthenticationToken(
			long authenticationTokenId, String name)
		throws PortalException {

		_authenticationTokenPermission.check(
			getPermissionChecker(), authenticationTokenId, ActionKeys.UPDATE);

		return authenticationTokenLocalService.updateAuthenticationToken(
			authenticationTokenId, name);
	}

	public AuthenticationToken updateStatus(
			long authenticationTokenId, int status)
		throws PortalException {

		_authenticationTokenPermission.check(
			getPermissionChecker(), authenticationTokenId, ActionKeys.UPDATE);

		return authenticationTokenLocalService.updateStatus(
			authenticationTokenId, status);
	}

	@Reference
	private AuthenticationTokenPermission _authenticationTokenPermission;

}