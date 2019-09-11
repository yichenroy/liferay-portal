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

package com.liferay.osb.koroneiki.phloem.rest.internal.security.auth.verifier;

import com.liferay.osb.koroneiki.scion.model.AuthenticationToken;
import com.liferay.osb.koroneiki.scion.model.ServiceProducer;
import com.liferay.osb.koroneiki.scion.service.AuthenticationTokenLocalService;
import com.liferay.osb.koroneiki.scion.service.ServiceProducerLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	immediate = true,
	property = "auth.verifier.ServiceProducerAuthVerifier.urls.includes=/o/koroneiki-rest/*",
	service = AuthVerifier.class
)
public class ServiceProducerAuthVerifier implements AuthVerifier {

	@Override
	public String getAuthType() {
		return ServiceProducerAuthVerifier.class.getSimpleName();
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		try {
			String[] credentials = verify(accessControlContext.getRequest());

			if (credentials != null) {
				authVerifierResult.setPassword(credentials[1]);
				authVerifierResult.setPasswordBasedAuthentication(true);
				authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
				authVerifierResult.setUserId(Long.valueOf(credentials[0]));
			}
		}
		catch (AuthException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae, ae);
			}

			HttpServletResponse httpServletResponse =
				accessControlContext.getResponse();

			try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					httpServletResponse.getOutputStream())) {

				objectOutputStream.writeObject(ae);

				authVerifierResult.setState(
					AuthVerifierResult.State.INVALID_CREDENTIALS);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);

				throw ae;
			}
		}

		return authVerifierResult;
	}

	protected String[] verify(HttpServletRequest httpServletRequest)
		throws AuthException {

		String apiToken = httpServletRequest.getHeader("API_Token");

		if (Validator.isNull(apiToken)) {
			return null;
		}

		String digest = DigesterUtil.digestBase64(Digester.SHA_256, apiToken);

		AuthenticationToken authenticationToken =
			_authenticationTokenLocalService.fetchAuthenticationToken(
				digest, WorkflowConstants.STATUS_APPROVED);

		if (authenticationToken == null) {
			return null;
		}

		ServiceProducer serviceProducer =
			_serviceProducerLocalService.fetchServiceProducer(
				authenticationToken.getServiceProducerId());

		if (serviceProducer == null) {
			return null;
		}

		String[] credentials = new String[2];

		credentials[0] = String.valueOf(
			serviceProducer.getAuthorizationUserId());
		credentials[1] = StringPool.BLANK;

		return credentials;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceProducerAuthVerifier.class);

	@Reference
	private AuthenticationTokenLocalService _authenticationTokenLocalService;

	@Reference
	private ServiceProducerLocalService _serviceProducerLocalService;

}