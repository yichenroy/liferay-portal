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

package com.liferay.portal.workflow.kaleo.forms.util.comparator;

import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

/**
 * Orders Kaleo processes according to their modified dates during listing
 * operations. The order can be ascending or descending and is defined by the
 * value specified in the class constructor.
 *
 * @author Inácio Nery
 * @see    com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalService#getKaleoProcesses(
 *         long, int, int, OrderByComparator)
 */
public class KaleoProcessModifiedDateComparator
	extends StagedModelModifiedDateComparator<KaleoProcess> {

	public KaleoProcessModifiedDateComparator() {
		this(false);
	}

	public KaleoProcessModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public String getOrderBy() {
		return "KaleoProcess." + super.getOrderBy();
	}

}