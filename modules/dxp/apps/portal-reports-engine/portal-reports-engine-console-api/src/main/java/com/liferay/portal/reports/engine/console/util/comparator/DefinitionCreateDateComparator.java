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

package com.liferay.portal.reports.engine.console.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.reports.engine.console.model.Definition;

/**
 * @author Rafael Praxedes
 */
public class DefinitionCreateDateComparator
	extends OrderByComparator<Definition> {

	public static final String ORDER_BY_ASC =
		"Reports_Definition.createDate ASC";

	public static final String ORDER_BY_DESC =
		"Reports_Definition.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public DefinitionCreateDateComparator() {
		this(false);
	}

	public DefinitionCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Definition definition1, Definition definition2) {
		int value = DateUtil.compareTo(
			definition1.getCreateDate(), definition2.getCreateDate());

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}