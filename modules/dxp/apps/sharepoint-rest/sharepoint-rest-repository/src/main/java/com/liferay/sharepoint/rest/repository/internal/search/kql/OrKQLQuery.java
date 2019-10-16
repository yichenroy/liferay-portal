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

package com.liferay.sharepoint.rest.repository.internal.search.kql;

/**
 * @author Adolfo Pérez
 */
public class OrKQLQuery implements KQLQuery {

	public OrKQLQuery(KQLQuery kqlQuery1, KQLQuery kqlQuery2) {
		_kqlQuery1 = kqlQuery1;
		_kqlQuery2 = kqlQuery2;
	}

	@Override
	public String toString() {
		return String.format(
			"(%s OR %s)", _kqlQuery1.toString(), _kqlQuery2.toString());
	}

	private final KQLQuery _kqlQuery1;
	private final KQLQuery _kqlQuery2;

}