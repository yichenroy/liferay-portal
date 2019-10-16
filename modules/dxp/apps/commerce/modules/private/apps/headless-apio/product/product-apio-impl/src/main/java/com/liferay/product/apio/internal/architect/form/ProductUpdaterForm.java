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

package com.liferay.product.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * Instances of this class represent the values extracted from a product form.
 *
 * @author Zoltán Takács
 * @review
 */
public class ProductUpdaterForm {

	/**
	 * Builds a {@code Form} that generates {@code ProductUpdaterForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a product updater form
	 * @review
	 */
	public static Form<ProductUpdaterForm> buildForm(
		Builder<ProductUpdaterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The product updater form"
		).description(
			__ -> "This form can be used to update a product"
		).constructor(
			ProductUpdaterForm::new
		).addOptionalLong(
			"folder", ProductUpdaterForm::_setFolder
		).addRequiredLong(
			"group", ProductUpdaterForm::_setGroup
		).addRequiredLong(
			"user", ProductUpdaterForm::_setUser
		).addRequiredLong(
			"version", ProductUpdaterForm::_setVersion
		).addRequiredString(
			"description", ProductUpdaterForm::_setDescription
		).addRequiredString(
			"text", ProductUpdaterForm::_setText
		).addRequiredString(
			"title", ProductUpdaterForm::_setTitle
		).build();
	}

	/**
	 * Returns the web page element's description map.
	 *
	 * @return the web page element's description map
	 * @review
	 */
	public Map<Locale, String> getDescriptionMap() {
		return Collections.singletonMap(Locale.getDefault(), _description);
	}

	/**
	 * Returns the folder ID if added through the form. Returns {@code 0}
	 * otherwise.
	 *
	 * @return the web page element's folder ID if present; {@code 0} otherwise
	 * @review
	 */
	public long getFolder() {
		if (_folder == null) {
			return 0;
		}

		return _folder;
	}

	/**
	 * Returns the web page element group's ID.
	 *
	 * @return the web page element group's ID
	 * @review
	 */
	public Long getGroup() {
		return _group;
	}

	/**
	 * Returns the web page element's text.
	 *
	 * @return the web page element's text
	 * @review
	 */
	public String getText() {
		return _text;
	}

	/**
	 * Returns the web page element's title map.
	 *
	 * @return the web page element's title map
	 * @review
	 */
	public Map<Locale, String> getTitleMap() {
		return Collections.singletonMap(Locale.getDefault(), _title);
	}

	/**
	 * Returns the web page element user's ID.
	 *
	 * @return the web page element user's ID
	 * @review
	 */
	public Long getUser() {
		return _user;
	}

	/**
	 * Returns the web page element version's ID.
	 *
	 * @return the web page element version's ID
	 * @review
	 */
	public Long getVersion() {
		return _version;
	}

	private void _setDescription(String description) {
		_description = description;
	}

	private void _setFolder(long folder) {
		_folder = folder;
	}

	private void _setGroup(Long group) {
		_group = group;
	}

	private void _setText(String text) {
		_text = text;
	}

	private void _setTitle(String title) {
		_title = title;
	}

	private void _setUser(Long user) {
		_user = user;
	}

	private void _setVersion(Long version) {
		_version = version;
	}

	private String _description;
	private Long _folder;
	private Long _group;
	private String _text;
	private String _title;
	private Long _user;
	private Long _version;

}