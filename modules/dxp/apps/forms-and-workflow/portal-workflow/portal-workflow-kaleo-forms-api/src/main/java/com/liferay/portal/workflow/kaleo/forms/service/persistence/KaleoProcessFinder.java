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

package com.liferay.portal.workflow.kaleo.forms.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Marcellus Tavares
 * @generated
 */
@ProviderType
public interface KaleoProcessFinder {
	public int countByKeywords(long groupId, java.lang.String keywords);

	public int countByG_N_D(long groupId, java.lang.String name,
		java.lang.String description, boolean andOperator);

	public int filterCountByKeywords(long groupId, java.lang.String keywords);

	public int filterCountByG_N_D(long groupId, java.lang.String name,
		java.lang.String description, boolean andOperator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> filterFindByKeywords(
		long groupId, java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> filterFindByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> filterFindByG_N_D(
		long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> findByKeywords(
		long groupId, java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> findByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);

	public java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> findByG_N_D(
		long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> orderByComparator);
}