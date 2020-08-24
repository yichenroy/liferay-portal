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

package com.liferay.remote.app.admin.web.internal.frontend.taglib.clay.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "clay.data.set.display.name=" + RemoteAppAdminConstants.REMOTE_APP_ENTRY_DATA_SET_DISPLAY,
	service = ClayDataSetDisplayView.class
)
public class RemoteAppEntryTableClayDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		_addClayTableSchemaField(
			clayTableSchemaBuilder, "name", "name", "actionLink");
		_addClayTableSchemaField(clayTableSchemaBuilder, "url", "url");

		return clayTableSchemaBuilder.build();
	}

	private void _addClayTableSchemaField(
		ClayTableSchemaBuilder clayTableSchemaBuilder, String fieldName,
		String label) {

		_addClayTableSchemaField(
			clayTableSchemaBuilder, fieldName, label, null);
	}

	private void _addClayTableSchemaField(
		ClayTableSchemaBuilder clayTableSchemaBuilder, String fieldName,
		String label, String contentRenderer) {

		ClayTableSchemaField clayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(fieldName, label);

		if (contentRenderer != null) {
			clayTableSchemaField.setContentRenderer(contentRenderer);
		}
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}