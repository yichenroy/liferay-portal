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

package com.liferay.osb.koroneiki.taproot.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface ContactFinder {

	public int countByFN_MN_LN_E(
		String firstName, String middleName, String lastName,
		String emailAddress, java.util.LinkedHashMap<String, Object> params);

	public java.util.List<com.liferay.osb.koroneiki.taproot.model.Contact>
		findByFN_MN_LN_E(
			String firstName, String middleName, String lastName,
			String emailAddress, java.util.LinkedHashMap<String, Object> params,
			int start, int end);

}