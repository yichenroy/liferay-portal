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

package com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.taglib.internal.security.permission.DDMFormInstancePermission;
import com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib.base.BaseDDMFormRendererTag;
import com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib.util.DDMFormTaglibUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pedro Queiroz
 * @author Rafael Praxedes
 */
public class DDMFormRendererTag extends BaseDDMFormRendererTag {

	protected DDMFormField createCaptchaField() {
		DDMFormField captchaDDMFormField = new DDMFormField(
			_DDM_FORM_FIELD_NAME_CAPTCHA, "captcha");

		captchaDDMFormField.setDataType("string");
		captchaDDMFormField.setProperty("url", createCaptchaResourceURL());

		return captchaDDMFormField;
	}

	protected String createCaptchaResourceURL() {
		RenderResponse renderResponse = getRenderResponse();

		ResourceURL resourceURL = renderResponse.createResourceURL();

		resourceURL.setResourceID("captcha");

		return resourceURL.toString();
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		DDMForm ddmForm) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		DDMFormInstance ddmFormInstance = getDDMFormInstance();

		ddmFormRenderingContext.setContainerId(StringUtil.randomString());

		ddmFormRenderingContext.setGroupId(ddmFormInstance.getGroupId());

		ddmFormRenderingContext.setHttpServletRequest(request);

		RenderResponse renderResponse = getRenderResponse();

		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(renderResponse));

		ddmFormRenderingContext.setLocale(getLocale(request, ddmForm));
		ddmFormRenderingContext.setViewMode(true);

		setDDMFormValues(ddmFormRenderingContext, ddmForm);
		setPortletNamespace(ddmFormRenderingContext, renderResponse);
		setReadOnly(ddmFormRenderingContext);
		setSubmitButton(ddmFormRenderingContext, ddmFormInstance);

		return ddmFormRenderingContext;
	}

	protected DDMFormLayoutRow createFullColumnDDMFormLayoutRow(
		String ddmFormFieldName) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			DDMFormLayoutColumn.FULL, ddmFormFieldName);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn);

		return ddmFormLayoutRow;
	}

	protected DDMForm getDDMForm() {
		DDMForm ddmForm = null;

		try {
			DDMFormInstance ddmFormInstance = getDDMFormInstance();

			if (ddmFormInstance == null) {
				return ddmForm;
			}

			DDMFormInstanceVersion latestDDMFormInstanceVersion =
				DDMFormTaglibUtil.getLatestDDMFormInstanceVersion(
					ddmFormInstance.getFormInstanceId(),
					WorkflowConstants.STATUS_APPROVED);

			DDMStructureVersion ddmStructureVersion =
				latestDDMFormInstanceVersion.getStructureVersion();

			ddmForm = ddmStructureVersion.getDDMForm();

			if (isCaptchaRequired(ddmFormInstance)) {
				ddmForm.addDDMFormField(createCaptchaField());
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return ddmForm;
	}

	protected String getDDMFormHTML() {
		String ddmFormHTML = StringPool.BLANK;

		DDMForm ddmForm = getDDMForm();
		DDMFormLayout ddmFormLayout = getDDMFormLayout();

		if ((ddmForm == null) || (ddmFormLayout == null)) {
			return ddmFormHTML;
		}

		try {
			ddmFormHTML = DDMFormTaglibUtil.renderForm(
				ddmForm, ddmFormLayout, createDDMFormRenderingContext(ddmForm));
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return ddmFormHTML;
	}

	protected DDMFormInstance getDDMFormInstance() {
		long ddmFormInstanceId = 0;

		if (getDdmFormInstanceRecordVersionId() != null) {
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				DDMFormTaglibUtil.getDDMFormInstanceRecordVersion(
					getDdmFormInstanceRecordVersionId());

			ddmFormInstanceId =
				ddmFormInstanceRecordVersion.getFormInstanceId();
		}
		else if (getDdmFormInstanceRecordId() != null) {
			DDMFormInstanceRecord ddmFormInstanceRecord =
				DDMFormTaglibUtil.getDDMFormInstanceRecord(
					getDdmFormInstanceRecordId());

			ddmFormInstanceId = ddmFormInstanceRecord.getFormInstanceId();
		}
		else if (getDdmFormInstanceVersionId() != null) {
			DDMFormInstanceVersion ddmFormInstanceVersion =
				DDMFormTaglibUtil.getDDMFormInstanceVersion(
					getDdmFormInstanceVersionId());

			ddmFormInstanceId = ddmFormInstanceVersion.getFormInstanceId();
		}
		else if (getDdmFormInstanceId() != null) {
			ddmFormInstanceId = getDdmFormInstanceId();
		}

		return DDMFormTaglibUtil.getDDMFormInstance(ddmFormInstanceId);
	}

	protected DDMFormLayout getDDMFormLayout() {
		DDMFormLayout ddmFormLayout = null;

		try {
			DDMFormInstance ddmFormInstance = getDDMFormInstance();

			if (ddmFormInstance == null) {
				return ddmFormLayout;
			}

			DDMFormInstanceVersion latestDDMFormInstanceVersion =
				DDMFormTaglibUtil.getLatestDDMFormInstanceVersion(
					ddmFormInstance.getFormInstanceId(),
					WorkflowConstants.STATUS_APPROVED);

			DDMStructureVersion ddmStructureVersion =
				latestDDMFormInstanceVersion.getStructureVersion();

			ddmFormLayout = ddmStructureVersion.getDDMFormLayout();

			if (isCaptchaRequired(ddmFormInstance)) {
				DDMFormLayoutPage lastDDMFormLayoutPage =
					getLastDDMFormLayoutPage(ddmFormLayout);

				DDMFormLayoutRow ddmFormLayoutRow =
					createFullColumnDDMFormLayoutRow(
						_DDM_FORM_FIELD_NAME_CAPTCHA);

				lastDDMFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return ddmFormLayout;
	}

	protected DDMFormLayoutPage getLastDDMFormLayoutPage(
		DDMFormLayout ddmFormLayout) {

		List<DDMFormLayoutPage> ddmFormLayoutPages =
			ddmFormLayout.getDDMFormLayoutPages();

		return ddmFormLayoutPages.get(ddmFormLayoutPages.size() - 1);
	}

	protected Locale getLocale(HttpServletRequest request, DDMForm ddmForm) {
		String languageId = LanguageUtil.getLanguageId(request);

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		if (ddmForm == null) {
			return locale;
		}

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		if (availableLocales.contains(locale)) {
			return locale;
		}

		return ddmForm.getDefaultLocale();
	}

	protected String getRedirectURL() {
		DDMFormInstance ddmFormInstance = getDDMFormInstance();

		if (ddmFormInstance == null) {
			return StringPool.BLANK;
		}

		try {
			DDMFormInstanceSettings ddmFormInstanceSettings =
				ddmFormInstance.getSettingsModel();

			return ddmFormInstanceSettings.redirectURL();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return StringPool.BLANK;
	}

	protected RenderResponse getRenderResponse() {
		return (RenderResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundleLoader portalResourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		ResourceBundle portalResourceBundle =
			portalResourceBundleLoader.loadResourceBundle(locale);

		ResourceBundle moduleResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			moduleResourceBundle, portalResourceBundle);
	}

	protected String getSubmitLabel(
		DDMFormInstance ddmFormInstance, Locale locale) {

		ThemeDisplay themeDisplay = getThemeDisplay();

		boolean workflowEnabled = hasWorkflowEnabled(
			ddmFormInstance, themeDisplay);

		ResourceBundle resourceBundle = getResourceBundle(locale);

		if (workflowEnabled) {
			return LanguageUtil.get(resourceBundle, "submit-for-publication");
		}

		return LanguageUtil.get(resourceBundle, "submit-form");
	}

	protected ThemeDisplay getThemeDisplay() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay;
	}

	protected boolean hasAddFormInstanceRecordPermission() {
		boolean hasAddFormInstanceRecordPermission = true;

		DDMFormInstance ddmFormInstance = getDDMFormInstance();

		if (ddmFormInstance != null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			try {
				hasAddFormInstanceRecordPermission =
					DDMFormInstancePermission.contains(
						themeDisplay.getPermissionChecker(), ddmFormInstance,
						DDMActionKeys.ADD_FORM_INSTANCE_RECORD);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return hasAddFormInstanceRecordPermission;
	}

	protected boolean hasViewFormInstancePermission() {
		boolean hasViewFormInstancePermission = true;

		DDMFormInstance ddmFormInstance = getDDMFormInstance();

		if (ddmFormInstance != null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			try {
				hasViewFormInstancePermission =
					DDMFormInstancePermission.contains(
						themeDisplay.getPermissionChecker(), ddmFormInstance,
						ActionKeys.VIEW);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return hasViewFormInstancePermission;
	}

	protected boolean hasWorkflowEnabled(
		DDMFormInstance ddmFormInstance, ThemeDisplay themeDisplay) {

		return DDMFormTaglibUtil.hasWorkflowDefinitionLink(
			themeDisplay.getCompanyId(), ddmFormInstance.getGroupId(),
			DDMFormInstance.class.getName(),
			ddmFormInstance.getFormInstanceId());
	}

	protected boolean isCaptchaRequired(DDMFormInstance ddmFormInstance) {
		boolean captchaRequired = false;

		try {
			DDMFormInstanceSettings ddmFormInstanceSettings =
				ddmFormInstance.getSettingsModel();

			captchaRequired = ddmFormInstanceSettings.requireCaptcha();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return captchaRequired;
	}

	protected boolean isFormAvailable() {
		DDMFormInstance ddmFormInstance = getDDMFormInstance();

		if (ddmFormInstance != null) {
			Group group = DDMFormTaglibUtil.getGroup(
				ddmFormInstance.getGroupId());

			if ((group != null) && group.isStagingGroup()) {
				return false;
			}

			if (!hasViewFormInstancePermission()) {
				return false;
			}

			return true;
		}

		return false;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		setNamespacedAttribute(request, "ddmFormHTML", getDDMFormHTML());
		setNamespacedAttribute(
			request, "ddmFormInstance", getDDMFormInstance());
		setNamespacedAttribute(
			request, "hasAddFormInstanceRecordPermission",
			hasAddFormInstanceRecordPermission());
		setNamespacedAttribute(
			request, "hasViewFormInstancePermission",
			hasViewFormInstancePermission());
		setNamespacedAttribute(request, "isFormAvailable", isFormAvailable());
		setNamespacedAttribute(
			request, "languageId",
			LocaleUtil.toLanguageId(getLocale(request, getDDMForm())));
		setNamespacedAttribute(request, "redirectURL", getRedirectURL());
		setNamespacedAttribute(
			request, "resourceBundle",
			getResourceBundle(getLocale(request, getDDMForm())));
	}

	protected void setDDMFormValues(
		DDMFormRenderingContext ddmFormRenderingContext, DDMForm ddmForm) {

		DDMFormValues ddmFormValues = DDMFormTaglibUtil.createDDMFormValues(
			request, ddmForm);

		try {
			if (getDdmFormInstanceRecordVersionId() != null) {
				DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
					DDMFormTaglibUtil.getDDMFormInstanceRecordVersion(
						getDdmFormInstanceRecordVersionId());

				if (ddmFormInstanceRecordVersion != null) {
					ddmFormValues = DDMFormTaglibUtil.mergeDDMFormValues(
						ddmFormInstanceRecordVersion.getDDMFormValues(),
						ddmFormValues);
				}
			}
			else if (getDdmFormInstanceRecordId() != null) {
				DDMFormInstanceRecord ddmFormInstanceRecord =
					DDMFormTaglibUtil.getDDMFormInstanceRecord(
						getDdmFormInstanceRecordId());

				if (ddmFormInstanceRecord != null) {
					ddmFormValues = DDMFormTaglibUtil.mergeDDMFormValues(
						ddmFormInstanceRecord.getDDMFormValues(),
						ddmFormValues);
				}
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
	}

	protected void setPortletNamespace(
		DDMFormRenderingContext ddmFormRenderingContext,
		RenderResponse renderResponse) {

		String namespace = getNamespace();

		if (Validator.isNull(namespace)) {
			namespace = renderResponse.getNamespace();
		}

		ddmFormRenderingContext.setPortletNamespace(namespace);
	}

	protected void setReadOnly(
		DDMFormRenderingContext ddmFormRenderingContext) {

		if (!hasAddFormInstanceRecordPermission()) {
			ddmFormRenderingContext.setReadOnly(true);
		}
	}

	protected void setSubmitButton(
		DDMFormRenderingContext ddmFormRenderingContext,
		DDMFormInstance ddmFormInstance) {

		if (GetterUtil.getBoolean(getShowSubmitButton())) {
			ddmFormRenderingContext.setShowSubmitButton(true);

			ddmFormRenderingContext.setSubmitLabel(
				getSubmitLabel(
					ddmFormInstance, ddmFormRenderingContext.getLocale()));
		}
		else {
			ddmFormRenderingContext.setShowSubmitButton(false);
		}
	}

	private static final String _DDM_FORM_FIELD_NAME_CAPTCHA = "_CAPTCHA_";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormRendererTag.class);

}