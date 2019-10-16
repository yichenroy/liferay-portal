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

package com.liferay.saml.persistence.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Mika Koivisto
 */
@ProviderType
public class NoSuchIdpSsoSessionException extends NoSuchModelException {

	public NoSuchIdpSsoSessionException() {
	}

	public NoSuchIdpSsoSessionException(String msg) {
		super(msg);
	}

	public NoSuchIdpSsoSessionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchIdpSsoSessionException(Throwable cause) {
		super(cause);
	}

}