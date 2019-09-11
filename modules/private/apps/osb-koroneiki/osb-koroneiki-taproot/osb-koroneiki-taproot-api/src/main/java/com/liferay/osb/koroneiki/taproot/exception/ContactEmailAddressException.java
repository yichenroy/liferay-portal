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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Amos Fong
 */
@ProviderType
public class ContactEmailAddressException extends PortalException {

	public ContactEmailAddressException() {
	}

	public ContactEmailAddressException(String msg) {
		super(msg);
	}

	public ContactEmailAddressException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ContactEmailAddressException(Throwable cause) {
		super(cause);
	}

	public static class MustNotBeDuplicate
		extends ContactEmailAddressException {

		public MustNotBeDuplicate(String emailAddress) {
			super(
				String.format(
					"A user with email address %s is already in use",
					emailAddress));
		}

	}

}