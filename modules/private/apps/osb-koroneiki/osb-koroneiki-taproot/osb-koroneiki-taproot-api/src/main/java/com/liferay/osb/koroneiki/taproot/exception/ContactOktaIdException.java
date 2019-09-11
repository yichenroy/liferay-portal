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

package com.liferay.osb.koroneiki.taproot.exception;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Amos Fong
 */
@ProviderType
public class ContactOktaIdException extends PortalException {

	public ContactOktaIdException() {
	}

	public ContactOktaIdException(String msg) {
		super(msg);
	}

	public ContactOktaIdException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ContactOktaIdException(Throwable cause) {
		super(cause);
	}

	public static class MustNotBeDuplicate extends ContactOktaIdException {

		public MustNotBeDuplicate(String oktaId) {
			super(
				String.format(
					"A contact with oktaId %s is already in use", oktaId));
		}

	}

}