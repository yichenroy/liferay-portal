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

package com.liferay.talend.connection;

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.exception.ExceptionUtils;
import com.liferay.talend.properties.WebSiteProperty;
import com.liferay.talend.runtime.LiferaySourceOrSinkRuntime;
import com.liferay.talend.tliferayconnection.TLiferayConnectionDefinition;
import com.liferay.talend.utils.PropertiesUtils;

import java.io.IOException;

import java.util.EnumSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.sandbox.SandboxedInstance;

/**
 * @author Zoltán Takács
 */
public class LiferayConnectionProperties
	extends ComponentPropertiesImpl
	implements LiferayConnectionPropertiesProvider {

	public static final String FORM_WIZARD = "Wizard";

	public LiferayConnectionProperties(String name) {
		super(name);
	}

	public void afterAnonymousLogin() {
		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(FORM_WIZARD));
	}

	public void afterEndpoint() {
		webSiteProperty.setHost(endpoint.getValue());
	}

	public void afterReferencedComponent() {
		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public void afterSiteFilter() {
		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public ValidationResult afterWebSiteProperty() {
		if (_log.isDebugEnabled()) {
			_log.debug("Website URL: " + webSiteProperty.getWebSiteURL());
		}

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		validationResultMutable.setStatus(ValidationResult.Result.OK);

		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(
				null, getReferencedConnectionProperties());

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			validationResultMutable.setMessage(validationResult.getMessage());
			validationResultMutable.setStatus(validationResult.getStatus());

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.OK) {

				try {
					webSiteName.setValue(
						liferaySourceOrSinkRuntime.getActualWebSiteName(
							webSiteProperty.getWebSiteURL()));
				}
				catch (IOException ioe) {
					validationResult =
						ExceptionUtils.exceptionToValidationResult(ioe);

					validationResultMutable.setMessage(
						validationResult.getMessage());
					validationResultMutable.setStatus(
						validationResult.getStatus());
				}
			}
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));

		return validationResultMutable;
	}

	public ValidationResult beforeWebSiteProperty() {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(
				null, getReferencedConnectionProperties());

			ValidationResultMutable validationResultMutable =
				new ValidationResultMutable();

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			validationResultMutable.setStatus(validationResult.getStatus());

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.OK) {

				try {
					List<NamedThing> webSites =
						liferaySourceOrSinkRuntime.getAvailableWebSites();

					if (webSites.isEmpty()) {
						validationResultMutable.setMessage(
							i18nMessages.getMessage(
								"error.validation.websites"));
						validationResultMutable.setStatus(
							ValidationResult.Result.ERROR);
					}

					webSiteProperty.setPossibleNamedThingValues(webSites);
				}
				catch (Exception e) {
					return ExceptionUtils.exceptionToValidationResult(e);
				}
			}

			return validationResultMutable;
		}
	}

	@Override
	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return this;
	}

	public String getReferencedComponentId() {
		return referencedComponent.componentInstanceId.getStringValue();
	}

	public LiferayConnectionProperties getReferencedConnectionProperties() {
		LiferayConnectionProperties liferayConnectionProperties =
			referencedComponent.getReference();

		if (liferayConnectionProperties != null) {
			return liferayConnectionProperties;
		}

		_log.error(
			"Connection has a reference to '{}' but the referenced Object is " +
				"null",
			getReferencedComponentId());

		return getLiferayConnectionProperties();
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);

		String referencedComponentId = getReferencedComponentId();

		boolean useOtherConnection = false;

		if ((referencedComponentId != null) &&
			referencedComponentId.startsWith(
				TLiferayConnectionDefinition.COMPONENT_NAME)) {

			useOtherConnection = true;
		}

		PropertiesUtils.setHidden(form, anonymousLogin, useOtherConnection);
		PropertiesUtils.setHidden(form, endpoint, useOtherConnection);
		PropertiesUtils.setHidden(form, loginType, useOtherConnection);
		PropertiesUtils.setHidden(form, password, useOtherConnection);
		PropertiesUtils.setHidden(form, siteFilter, useOtherConnection);
		PropertiesUtils.setHidden(form, userId, useOtherConnection);
		PropertiesUtils.setHidden(form, webSiteName, useOtherConnection);
		PropertiesUtils.setHidden(form, webSiteProperty, useOtherConnection);

		if (!useOtherConnection && anonymousLogin.getValue()) {
			PropertiesUtils.setHidden(form, userId, true);
			PropertiesUtils.setHidden(form, password, true);
		}

		if (!useOtherConnection && !siteFilter.getValue()) {
			PropertiesUtils.setHidden(form, webSiteName, true);
			PropertiesUtils.setHidden(form, webSiteProperty, true);
		}
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		// Wizard form

		Form wizardForm = Form.create(this, FORM_WIZARD);

		Widget loginWizardWidget = Widget.widget(loginType);

		loginWizardWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);
		loginWizardWidget.setDeemphasize(true);

		wizardForm.addRow(loginWizardWidget);

		wizardForm.addRow(name);

		wizardForm.addRow(endpoint);

		wizardForm.addRow(userId);

		wizardForm.addRow(password);

		wizardForm.addRow(anonymousLogin);

		Widget testConnectionWidget = Widget.widget(testConnection);

		testConnectionWidget.setLongRunning(true);
		testConnectionWidget.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		Widget advancedFormWizardWidget = Widget.widget(advanced);

		advancedFormWizardWidget.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		wizardForm.addRow(advancedFormWizardWidget);

		wizardForm.addColumn(testConnectionWidget);

		// Main form

		Form mainForm = Form.create(this, Form.MAIN);

		Widget loginMainWidget = Widget.widget(loginType);

		loginMainWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);

		mainForm.addRow(loginMainWidget);

		mainForm.addRow(endpoint);

		mainForm.addRow(userId);

		mainForm.addRow(password);

		mainForm.addRow(anonymousLogin);

		mainForm.addRow(siteFilter);

		Widget webSiteURLWidget = Widget.widget(webSiteProperty);

		webSiteURLWidget.setCallAfter(true);
		webSiteURLWidget.setLongRunning(true);
		webSiteURLWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		mainForm.addRow(webSiteURLWidget);

		Widget webSiteNameWidget = Widget.widget(webSiteName);

		webSiteNameWidget.setReadonly(true);

		mainForm.addColumn(webSiteNameWidget);

		// A form for a reference to a connection, used in a tLiferayInput
		// for example

		Form referenceForm = Form.create(this, Form.REFERENCE);

		Widget referencedComponentWidget = Widget.widget(referencedComponent);

		referencedComponentWidget.setWidgetType(
			Widget.COMPONENT_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(referencedComponentWidget);

		Widget loginReferenceWidget = Widget.widget(loginType);

		loginReferenceWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);

		referenceForm.addRow(loginReferenceWidget);

		referenceForm.addRow(endpoint);

		referenceForm.addRow(userId);

		referenceForm.addRow(password);

		referenceForm.addRow(anonymousLogin);

		referenceForm.addRow(siteFilter);

		Widget webSitePropertyReferenceWidget = Widget.widget(webSiteProperty);

		webSitePropertyReferenceWidget.setCallAfter(true);
		webSitePropertyReferenceWidget.setLongRunning(true);
		webSitePropertyReferenceWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(webSitePropertyReferenceWidget);

		Widget webSiteNameReferenceWidget = Widget.widget(webSiteName);

		webSiteNameReferenceWidget.setReadonly(true);

		referenceForm.addColumn(webSiteNameReferenceWidget);

		refreshLayout(referenceForm);

		// Advanced form

		Form advancedForm = new Form(this, Form.ADVANCED);

		advancedForm.addRow(connectTimeout);

		advancedForm.addRow(readTimeout);

		advancedForm.addRow(itemsPerPage);

		advancedForm.addRow(followRedirects);

		advancedForm.addRow(forceHttps);

		advanced.setFormtoShow(advancedForm);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		endpoint.setValue(_HOST);
		followRedirects.setValue(true);
		forceHttps.setValue(false);
		loginType.setValue(LoginType.Basic);
		password.setValue(_PASSWORD);
		siteFilter.setValue(false);
		userId.setValue(_USER_ID);
		webSiteName.setValue("");
		webSiteProperty.setHost(endpoint.getValue());
		webSiteProperty.setValue("");
	}

	public ValidationResult validateTestConnection() {
		try {
			SandboxedInstance sandboxedInstance = getRuntimeSandboxedInstance();

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(null, this);

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			Form form = getForm(FORM_WIZARD);

			if (validationResult.getStatus() == ValidationResult.Result.OK) {
				form.setAllowFinish(true);
				form.setAllowForward(true);
			}
			else {
				form.setAllowForward(false);
			}

			return validationResult;
		}
		catch (Exception e) {
			return new ValidationResult(
				ValidationResult.Result.ERROR, e.getMessage());
		}
	}

	public PresentationItem advanced = new PresentationItem("advanced");
	public Property<Boolean> anonymousLogin = PropertyFactory.newBoolean(
		"anonymousLogin");
	public Property<Integer> connectTimeout = PropertyFactory.newInteger(
		"connectTimeout", _CONNECT_TIMEOUT);
	public Property<String> endpoint = PropertyFactory.newString("endpoint");
	public Property<Boolean> followRedirects = PropertyFactory.newBoolean(
		"followRedirects");
	public Property<Boolean> forceHttps = PropertyFactory.newBoolean(
		"forceHttps");
	public Property<Integer> itemsPerPage = PropertyFactory.newInteger(
		"itemsPerPage", _ITEMS_PER_PAGE);
	public Property<LoginType> loginType = PropertyFactory.newEnum(
		"loginType", LoginType.class).setRequired();
	public Property<String> name = PropertyFactory.newString(
		"name").setRequired();
	public Property<String> password =
		PropertyFactory.newString("password").setFlags(
			EnumSet.of(
				Property.Flags.ENCRYPT, Property.Flags.SUPPRESS_LOGGING));
	public Property<Integer> readTimeout = PropertyFactory.newInteger(
		"readTimeout", _READ_TIMEOUT);
	public ComponentReferenceProperties<LiferayConnectionProperties>
		referencedComponent = new ComponentReferenceProperties<>(
			"referencedComponent", TLiferayConnectionDefinition.COMPONENT_NAME);
	public Property<Boolean> siteFilter = PropertyFactory.newBoolean(
		"siteFilter");
	public PresentationItem testConnection = new PresentationItem(
		"testConnection");
	public Property<String> userId = PropertyFactory.newString("userId");
	public Property<String> webSiteName = PropertyFactory.newString(
		"webSiteName");
	public WebSiteProperty webSiteProperty = new WebSiteProperty(
		"webSiteProperty");

	public enum LoginType {

		Basic("Basic Authentication");

		public String getDescription() {
			return _description;
		}

		private LoginType(String description) {
			_description = description;
		}

		private final String _description;

	}

	protected SandboxedInstance getRuntimeSandboxedInstance() {
		return LiferayBaseComponentDefinition.getSandboxedInstance(
			LiferayBaseComponentDefinition.RUNTIME_SOURCE_OR_SINK_CLASS_NAME);
	}

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferayConnectionProperties.class);
	}

	private static final int _CONNECT_TIMEOUT = 30;

	private static final String _HOST = "\"http://localhost:8080/o/api\"";

	private static final int _ITEMS_PER_PAGE = 100;

	private static final String _PASSWORD = "test";

	private static final int _READ_TIMEOUT = 60;

	private static final String _USER_ID = "test@liferay.com";

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayConnectionProperties.class);

	private static final long serialVersionUID = -746398918369840241L;

}