/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.options.web.internal.util;

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.util.comparator.CPOptionNameComparator;
import com.liferay.commerce.product.util.comparator.CPOptionValuePriorityComparator;
import com.liferay.commerce.product.util.comparator.CPOptionValueTitleComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPOptionsPortletUtil {

	public static OrderByComparator<CPOption> getCPOptionOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPOption> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new CPOptionNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CPOptionValue>
		getCPOptionValueOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPOptionValue> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = new CPOptionValuePriorityComparator(orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new CPOptionValueTitleComparator(orderByAsc);
		}

		return orderByComparator;
	}

}