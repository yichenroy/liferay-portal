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

package com.liferay.commerce.util;

import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommerceTierPriceEntry;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.util.comparator.CommercePriceEntryCreateDateComparator;
import com.liferay.commerce.util.comparator.CommercePriceListCreateDateComparator;
import com.liferay.commerce.util.comparator.CommercePriceListDisplayDateComparator;
import com.liferay.commerce.util.comparator.CommerceTierPriceEntryCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceWarehouseCityComparator;
import com.liferay.commerce.util.comparator.CommerceWarehouseItemQuantityComparator;
import com.liferay.commerce.util.comparator.CommerceWarehouseItemWarehouseNameComparator;
import com.liferay.commerce.util.comparator.CommerceWarehouseNameComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Objects;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceUtil {

	public static OrderByComparator<CommercePriceEntry>
		getCommercePriceEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommercePriceEntry> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommercePriceEntryCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommercePriceEntrySort(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(Field.CREATE_DATE, orderByAsc);
		}

		return sort;
	}

	public static OrderByComparator<CommercePriceList>
		getCommercePriceListOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommercePriceList> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommercePriceListCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new CommercePriceListDisplayDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommercePriceListSort(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(Field.CREATE_DATE, orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create("display-date", orderByAsc);
		}

		return sort;
	}

	public static OrderByComparator<CommerceTierPriceEntry>
		getCommerceTierPriceEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceTierPriceEntry> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceTierPriceEntryCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommerceTierPriceEntrySort(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = true;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(Field.CREATE_DATE, orderByAsc);
		}

		return sort;
	}

	public static OrderByComparator<CommerceWarehouseItem>
		getCommerceWarehouseItemOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceWarehouseItem> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator =
				new CommerceWarehouseItemWarehouseNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("quantity")) {
			orderByComparator = new CommerceWarehouseItemQuantityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceWarehouse>
		getCommerceWarehouseOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceWarehouse> orderByComparator = null;

		if (orderByCol.equals("city")) {
			orderByComparator = new CommerceWarehouseCityComparator(orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new CommerceWarehouseNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

}