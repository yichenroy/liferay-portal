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

import com.liferay.portal.kernel.exception.NoSuchModelException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class NoSuchTeamRoleException extends NoSuchModelException {

	public NoSuchTeamRoleException() {
	}

	public NoSuchTeamRoleException(String msg) {
		super(msg);
	}

	public NoSuchTeamRoleException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchTeamRoleException(Throwable cause) {
		super(cause);
	}

}