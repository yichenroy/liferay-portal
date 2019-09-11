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
public class TeamNameException extends PortalException {

	public TeamNameException() {
	}

	public TeamNameException(String msg) {
		super(msg);
	}

	public TeamNameException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public TeamNameException(Throwable cause) {
		super(cause);
	}

	public static class MustNotBeDuplicate extends TeamNameException {

		public MustNotBeDuplicate(String name, String accountName) {
			super(
				String.format(
					"A team with name %s on account %s is already in use", name,
					accountName));
		}

	}

}