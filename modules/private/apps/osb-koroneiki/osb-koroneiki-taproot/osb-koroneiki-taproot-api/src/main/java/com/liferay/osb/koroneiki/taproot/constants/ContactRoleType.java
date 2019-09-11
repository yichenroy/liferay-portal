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

package com.liferay.osb.koroneiki.taproot.constants;

import java.util.Objects;

/**
 * @author Amos Fong
 */
public class ContactRoleType {

	public static final int ACCOUNT = 1;

	public static final String ACCOUNT_LABEL = "account";

	public static final int TEAM = 2;

	public static final String TEAM_LABEL = "team";

	public static final int[] VALUES = {ACCOUNT, TEAM};

	public static int fromLabel(String label) {
		if (Objects.equals(ACCOUNT_LABEL, label)) {
			return ACCOUNT;
		}
		else if (Objects.equals(TEAM_LABEL, label)) {
			return TEAM;
		}

		throw new IllegalArgumentException("Invalid type label " + label);
	}

	public static String getLabel(int value) {
		if (value == ACCOUNT) {
			return ACCOUNT_LABEL;
		}
		else if (value == TEAM) {
			return TEAM_LABEL;
		}
		else {
			return null;
		}
	}

}