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

package com.liferay.sharepoint.connector.schema.query.operator;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.connector.schema.query.QueryField;
import com.liferay.sharepoint.connector.schema.query.QueryValue;

/**
 * @author Iván Zaera
 */
public abstract class BaseMultiValueOperator extends BaseOperator {

	public BaseMultiValueOperator(
		QueryField queryField, QueryValue... queryValues) {

		super(queryField);

		_queryValues = queryValues;
	}

	@Override
	protected void populate(Element element) {
		super.populate(element);

		Element valuesElement = element.addElement("Values");

		for (QueryValue queryValue : _queryValues) {
			queryValue.attach(valuesElement);
		}
	}

	private final QueryValue[] _queryValues;

}