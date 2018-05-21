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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.form.web.internal.constants.DDMFormWebKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.DDMFormAdminRequestHelper;
import com.liferay.dynamic.data.mapping.form.web.internal.instance.lifecycle.AddDefaultSharedFormLayoutPortalInstanceLifecycleListener;
import com.liferay.dynamic.data.mapping.form.web.internal.search.FormInstanceSearch;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormInstancePermission;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormPermission;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.exporter.DDMExporterFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.dynamic.data.mapping.util.comparator.DDMFormInstanceModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.DDMFormInstanceNameComparator;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bruno Basto
 */
public class DDMFormAdminDisplayContext {

	public DDMFormAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
		DDMExporterFactory ddmExporterFactory,
		DDMFormWebConfiguration formWebConfiguration,
		DDMFormInstanceRecordLocalService formInstanceRecordLocalService,
		DDMFormInstanceService formInstanceService,
		DDMFormFieldTypeServicesTracker formFieldTypeServicesTracker,
		DDMFormFieldTypesJSONSerializer formFieldTypesJSONSerializer,
		DDMFormRenderer formRenderer, DDMFormValuesFactory formValuesFactory,
		DDMFormValuesMerger formValuesMerger,
		DDMStructureLocalService structureLocalService,
		DDMStructureService structureService, JSONFactory jsonFactory,
		StorageEngine storageEngine) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_addDefaultSharedFormLayoutPortalInstanceLifecycleListener =
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener;
		_ddmExporterFactory = ddmExporterFactory;
		_ddmFormWebConfiguration = formWebConfiguration;
		_ddmFormInstanceRecordLocalService = formInstanceRecordLocalService;
		_ddmFormInstanceService = formInstanceService;
		_ddmFormFieldTypeServicesTracker = formFieldTypeServicesTracker;
		_ddmFormFieldTypesJSONSerializer = formFieldTypesJSONSerializer;
		_ddmFormRenderer = formRenderer;
		_ddmFormValuesFactory = formValuesFactory;
		_ddmFormValuesMerger = formValuesMerger;
		_ddmStructureLocalService = structureLocalService;
		_ddmStructureService = structureService;
		_jsonFactory = jsonFactory;
		_storageEngine = storageEngine;

		formAdminRequestHelper = new DDMFormAdminRequestHelper(renderRequest);
	}

	public List<DropdownItem> getActionItemsDropdownItems() {
		return new DropdownItemList() {

			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteFormInstances");
						dropdownItem.setIcon("trash");
						dropdownItem.setLabel(
							LanguageUtil.get(
								formAdminRequestHelper.getRequest(), "delete"));
						dropdownItem.setQuickAction(true);
					});
			}

		};
	}

	public int getAutosaveInterval() {
		return _ddmFormWebConfiguration.autosaveInterval();
	}

	public Map<String, String> getAvailableExportFormats() {
		return _ddmExporterFactory.getAvailableFormatsMap();
	}

	public Locale[] getAvailableLocales() {
		Locale[] availableLocales = getFormBuilderContextAvailableLocales();

		if (availableLocales != null) {
			return availableLocales;
		}

		availableLocales = getFormAvailableLocales();

		if (availableLocales != null) {
			return availableLocales;
		}

		return new Locale[] {getSiteDefaultLocale()};
	}

	public String getClearResultsURL() throws PortletException {
		PortletURL clearResultsURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public long getCompanyId() {
		return formAdminRequestHelper.getCompanyId();
	}

	public CreationMenu getCreationMenu() {
		if (!isShowAddButton()) {
			return null;
		}

		return new CreationMenu() {
			{
				HttpServletRequest request =
					formAdminRequestHelper.getRequest();

				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(), "mvcPath",
							"/admin/edit_form_instance.jsp", "redirect",
							PortalUtil.getCurrentURL(request), "groupId",
							String.valueOf(themeDisplay.getScopeGroupId()));

						dropdownItem.setLabel(
							LanguageUtil.get(request, "new-form"));
					});
			}
		};
	}

	public String getCSVExport() {
		return _ddmFormWebConfiguration.csvExport();
	}

	public JSONArray getDDMFormFieldTypesJSONArray() throws PortalException {
		List<DDMFormFieldType> formFieldTypes =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		String serializedFormFieldTypes =
			_ddmFormFieldTypesJSONSerializer.serialize(formFieldTypes);

		return _jsonFactory.createJSONArray(serializedFormFieldTypes);
	}

	public String getDDMFormHTML(RenderRequest renderRequest)
		throws PortalException {

		DDMFormViewFormInstanceRecordDisplayContext
			formViewRecordDisplayContext = getFormViewRecordDisplayContext();

		return formViewRecordDisplayContext.getDDMFormHTML(renderRequest);
	}

	public DDMFormInstance getDDMFormInstance() throws PortalException {
		if (_ddmFormInstance != null) {
			return _ddmFormInstance;
		}

		long formInstanceId = ParamUtil.getLong(
			_renderRequest, "formInstanceId");

		if (formInstanceId > 0) {
			_ddmFormInstance = _ddmFormInstanceService.fetchFormInstance(
				formInstanceId);
		}
		else {
			DDMFormInstanceRecord formInstanceRecord =
				getDDMFormInstanceRecord();

			if (formInstanceRecord != null) {
				_ddmFormInstance = formInstanceRecord.getFormInstance();
			}
		}

		return _ddmFormInstance;
	}

	public DDMFormInstanceRecordVersion getDDMFormInstanceRecordVersion()
		throws PortalException {

		DDMFormInstanceRecord formInstanceRecord = getDDMFormInstanceRecord();

		return formInstanceRecord.getLatestFormInstanceRecordVersion();
	}

	public DDMStructure getDDMStructure() throws PortalException {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		DDMFormInstance formInstance = getDDMFormInstance();

		if (formInstance == null) {
			return null;
		}

		_ddmStructure = _ddmStructureLocalService.getStructure(
			formInstance.getStructureId());

		return _ddmStructure;
	}

	public long getDDMStructureId() throws PortalException {
		DDMStructure structure = getDDMStructure();

		if (structure == null) {
			return 0;
		}

		return structure.getStructureId();
	}

	public String getDefaultLanguageId() {
		String defaultLanguageId = getFormBuilderContextDefaultLanguageId();

		if (defaultLanguageId != null) {
			return defaultLanguageId;
		}

		defaultLanguageId = getFormDefaultLanguageId();

		if (defaultLanguageId != null) {
			return defaultLanguageId;
		}

		return LocaleUtil.toLanguageId(getSiteDefaultLocale());
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(
				_renderRequest, _ddmFormWebConfiguration, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest request = formAdminRequestHelper.getRequest();

		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "order-by"));
					});
			}
		};
	}

	public String getFormDescription() throws PortalException {
		DDMFormInstance formInstance = getDDMFormInstance();

		if (formInstance != null) {
			return LocalizationUtil.getLocalization(
				formInstance.getDescription(), getFormDefaultLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("description");
	}

	public String getFormLocalizedDescription() throws PortalException {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		DDMFormInstance formInstance = getDDMFormInstance();

		if (formInstance == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> descriptionMap =
				formInstance.getDescriptionMap();

			for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	public String getFormLocalizedName() throws PortalException {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		DDMFormInstance formInstance = getDDMFormInstance();

		if (formInstance == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> nameMap = formInstance.getNameMap();

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	public String getFormName() throws PortalException {
		DDMFormInstance formInstance = getDDMFormInstance();

		if (formInstance != null) {
			return LocalizationUtil.getLocalization(
				formInstance.getName(), getFormDefaultLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("name");
	}

	public String getFormURL() throws PortalException {
		return getFormURL(getDDMFormInstance());
	}

	public String getFormURL(DDMFormInstance formInstance)
		throws PortalException {

		String formURL = null;

		DDMFormInstanceSettings formInstanceSettings =
			formInstance.getSettingsModel();

		if (formInstanceSettings.requireAuthentication()) {
			formURL = getRestrictedFormURL();
		}
		else {
			formURL = getSharedFormURL();
		}

		return formURL;
	}

	public DDMFormViewFormInstanceRecordDisplayContext
		getFormViewRecordDisplayContext() {

		return new DDMFormViewFormInstanceRecordDisplayContext(
			formAdminRequestHelper.getRequest(),
			PortalUtil.getHttpServletResponse(_renderResponse),
			_ddmFormInstanceRecordLocalService, _ddmFormRenderer,
			_ddmFormValuesFactory, _ddmFormValuesMerger);
	}

	public DDMFormViewFormInstanceRecordsDisplayContext
			getFormViewRecordsDisplayContext()
		throws PortalException {

		return new DDMFormViewFormInstanceRecordsDisplayContext(
			_renderRequest, _renderResponse, getDDMFormInstance(),
			_ddmFormInstanceRecordLocalService,
			_ddmFormFieldTypeServicesTracker, _storageEngine);
	}

	public JSONFactory getJSONFactory() {
		return _jsonFactory;
	}

	public String getLexiconIconsPath() {
		ThemeDisplay themeDisplay = formAdminRequestHelper.getThemeDisplay();

		StringBundler sb = new StringBundler(3);

		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/lexicon/icons.svg");
		sb.append(StringPool.POUND);

		return sb.toString();
	}

	public List<NavigationItem> getNavigationItems() {
		HttpServletRequest request = formAdminRequestHelper.getRequest();

		String currentTab = ParamUtil.getString(request, "currentTab", "forms");

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(currentTab.equals("forms"));
						navigationItem.setHref(
							_renderResponse.createRenderURL(), "currentTab",
							"forms");
						navigationItem.setLabel(
							LanguageUtil.get(request, "forms"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							currentTab.equals("element-set"));
						navigationItem.setHref(
							_renderResponse.createRenderURL(), "currentTab",
							"element-set");
						navigationItem.setLabel(
							LanguageUtil.get(request, "element-sets"));
					});
			}
		};
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(_renderRequest, "orderByType", "desc");
	}

	public PermissionChecker getPermissionChecker() {
		return formAdminRequestHelper.getPermissionChecker();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter("currentTab", "forms");
		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String displayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public String getPublishedFormURL() throws PortalException {
		return getPublishedFormURL(_ddmFormInstance);
	}

	public String getPublishedFormURL(DDMFormInstance formInstance)
		throws PortalException {

		if (formInstance == null) {
			return StringPool.BLANK;
		}

		String formURL = getFormURL(formInstance);

		return formURL.concat(String.valueOf(formInstance.getFormInstanceId()));
	}

	public RenderRequest getRenderRequest() {
		return _renderRequest;
	}

	public RenderResponse getRenderResponse() {
		return _renderResponse;
	}

	public String getRestrictedFormURL() {
		return _addDefaultSharedFormLayoutPortalInstanceLifecycleListener.
			getFormLayoutURL(formAdminRequestHelper.getThemeDisplay(), true);
	}

	public long getScopeGroupId() {
		return formAdminRequestHelper.getScopeGroupId();
	}

	public SearchContainer<?> getSearch() {
		String displayStyle = getDisplayStyle();

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", displayStyle);

		FormInstanceSearch formInstanceSearch = new FormInstanceSearch(
			_renderRequest, portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMFormInstance> orderByComparator =
			getDDMFormInstanceOrderByComparator(orderByCol, orderByType);

		formInstanceSearch.setOrderByCol(orderByCol);
		formInstanceSearch.setOrderByComparator(orderByComparator);
		formInstanceSearch.setOrderByType(orderByType);

		if (formInstanceSearch.isSearch()) {
			formInstanceSearch.setEmptyResultsMessage("no-forms-were-found");
		}
		else {
			formInstanceSearch.setEmptyResultsMessage("there-are-no-forms");
		}

		setDDMFormInstanceSearchResults(formInstanceSearch);
		setDDMFormInstanceSearchTotal(formInstanceSearch);

		return formInstanceSearch;
	}

	public String getSearchActionURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));
		portletURL.setParameter("currentTab", "forms");

		return portletURL.toString();
	}

	public String getSearchContainerId() {
		return "formInstance";
	}

	public String getSharedFormURL() {
		return _addDefaultSharedFormLayoutPortalInstanceLifecycleListener.
			getFormLayoutURL(formAdminRequestHelper.getThemeDisplay(), false);
	}

	public String getSortingURL() throws Exception {
		PortletURL sortingURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		String orderByType = ParamUtil.getString(_renderRequest, "orderByType");

		sortingURL.setParameter(
			"orderByType", orderByType.equals("asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public DDMStructureService getStructureService() {
		return _ddmStructureService;
	}

	public int getTotalItems() {
		SearchContainer<?> searchContainer = getSearch();

		return searchContainer.getTotal();
	}

	public List<ViewTypeItem> getViewTypesItems() throws Exception {
		PortletURL portletURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		return new ViewTypeItemList(portletURL, getDisplayStyle()) {

			{
				String[] viewTypes = getDisplayViews();

				for (String viewType : viewTypes) {
					if (viewType.equals("descriptive")) {
						addListViewTypeItem();
					}
					else {
						addTableViewTypeItem();
					}
				}
			}

		};
	}

	public boolean isDisabledManagementBar() {
		if (hasResults()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isFormPublished() throws PortalException {
		return isFormPublished(getDDMFormInstance());
	}

	public boolean isFormPublished(DDMFormInstance formInstance)
		throws PortalException {

		if (formInstance == null) {
			return false;
		}

		DDMFormInstanceSettings formInstanceSettings =
			formInstance.getSettingsModel();

		return formInstanceSettings.published();
	}

	public boolean isShowAddButton() {
		return DDMFormPermission.contains(
			formAdminRequestHelper.getPermissionChecker(),
			formAdminRequestHelper.getScopeGroupId(),
			DDMActionKeys.ADD_FORM_INSTANCE);
	}

	public boolean isShowCopyFormInstanceButton() {
		return isShowAddButton();
	}

	public boolean isShowCopyURLFormInstanceIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.VIEW);
	}

	public boolean isShowDeleteFormInstanceIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.DELETE);
	}

	public boolean isShowEditFormInstanceIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.UPDATE);
	}

	public boolean isShowExportFormInstanceIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.VIEW);
	}

	public boolean isShowPermissionsIcon(DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.PERMISSIONS);
	}

	public boolean isShowViewEntriesFormInstanceIcon(
			DDMFormInstance formInstance)
		throws PortalException {

		return DDMFormInstancePermission.contains(
			formAdminRequestHelper.getPermissionChecker(), formInstance,
			ActionKeys.VIEW);
	}

	protected DDMForm getDDMForm() throws PortalException {
		DDMStructure structure = getDDMStructure();

		DDMForm form = new DDMForm();

		if (structure != null) {
			form = structure.getDDMForm();
		}

		return form;
	}

	protected OrderByComparator<DDMFormInstance>
		getDDMFormInstanceOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDMFormInstance> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new DDMFormInstanceModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new DDMFormInstanceNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	protected DDMFormInstanceRecord getDDMFormInstanceRecord() {
		long formInstanceRecordId = ParamUtil.getLong(
			_renderRequest, "formInstanceRecordId");

		if (formInstanceRecordId > 0) {
			return _ddmFormInstanceRecordLocalService.fetchFormInstanceRecord(
				formInstanceRecordId);
		}

		HttpServletRequest httpServletRequest =
			formAdminRequestHelper.getRequest();

		DDMFormInstanceRecord formInstanceRecord =
			(DDMFormInstanceRecord)httpServletRequest.getAttribute(
				DDMFormWebKeys.DYNAMIC_DATA_MAPPING_FORM_INSTANCE_RECORD);

		return formInstanceRecord;
	}

	protected String getDisplayStyle(
		PortletRequest portletRequest,
		DDMFormWebConfiguration formWebConfiguration, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN, "display-style",
				formWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(displayViews, displayStyle)) {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN, "display-style",
				displayStyle);
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(
							getPortletURL(), "navigation", "all");
						dropdownItem.setLabel(
							LanguageUtil.get(
								formAdminRequestHelper.getRequest(), "all"));
					});
			}
		};
	}

	protected Locale[] getFormAvailableLocales() {
		try {
			DDMStructure structure = getDDMStructure();

			if (structure == null) {
				return null;
			}

			DDMForm form = structure.getDDMForm();

			Set<Locale> availableLocales = form.getAvailableLocales();

			return availableLocales.toArray(
				new Locale[availableLocales.size()]);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return null;
		}
	}

	protected Locale[] getFormBuilderContextAvailableLocales() {
		String serializedFormBuilderContext = ParamUtil.getString(
			_renderRequest, "serializedFormBuilderContext");

		if (Validator.isNull(serializedFormBuilderContext)) {
			return null;
		}

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				serializedFormBuilderContext);

			JSONArray jsonArray = jsonObject.getJSONArray(
				"availableLanguageIds");

			Locale[] locales = new Locale[jsonArray.length()];

			for (int i = 0; i < jsonArray.length(); i++) {
				locales[i] = LocaleUtil.fromLanguageId(jsonArray.getString(i));
			}

			return locales;
		}
		catch (JSONException jsone) {
			_log.error("Unable to deserialize form context", jsone);

			return null;
		}
	}

	protected String getFormBuilderContextDefaultLanguageId() {
		String serializedFormBuilderContext = ParamUtil.getString(
			_renderRequest, "serializedFormBuilderContext");

		if (Validator.isNull(serializedFormBuilderContext)) {
			return null;
		}

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				serializedFormBuilderContext);

			return jsonObject.getString("defaultLanguageId");
		}
		catch (JSONException jsone) {
			_log.error("Unable to deserialize form context", jsone);

			return null;
		}
	}

	protected String getFormDefaultLanguageId() {
		try {
			DDMStructure structure = getDDMStructure();

			if (structure == null) {
				return null;
			}

			DDMForm form = structure.getDDMForm();

			return LocaleUtil.toLanguageId(form.getDefaultLocale());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return null;
		}
	}

	protected String getJSONObjectLocalizedPropertyFromRequest(
		String propertyName) {

		String propertyValue = ParamUtil.getString(
			formAdminRequestHelper.getRequest(), propertyName);

		if (Validator.isNull(propertyValue)) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = formAdminRequestHelper.getThemeDisplay();

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				propertyValue);

			String languageId = themeDisplay.getLanguageId();

			if (jsonObject.has(languageId)) {
				return jsonObject.getString(languageId);
			}

			return jsonObject.getString(getDefaultLanguageId());
		}
		catch (JSONException jsone) {
			_log.error(
				String.format(
					"Unable to deserialize JSON localized property \"%s\" " +
						"from request",
					propertyName),
				jsone);
		}

		return StringPool.BLANK;
	}

	protected String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	protected Consumer<DropdownItem> getOrderByDropdownItem(String orderByCol) {
		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(orderByCol);
		};
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(getOrderByDropdownItem("modified-date"));
				add(getOrderByDropdownItem("name"));
			}
		};
	}

	protected Locale getSiteDefaultLocale() {
		ThemeDisplay themeDisplay = formAdminRequestHelper.getThemeDisplay();

		return themeDisplay.getSiteDefaultLocale();
	}

	protected boolean hasResults() {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected void setDDMFormInstanceSearchResults(
		FormInstanceSearch ddmFormInstanceSearch) {

		List<DDMFormInstance> results = _ddmFormInstanceService.search(
			formAdminRequestHelper.getCompanyId(),
			formAdminRequestHelper.getScopeGroupId(), getKeywords(),
			ddmFormInstanceSearch.getStart(), ddmFormInstanceSearch.getEnd(),
			ddmFormInstanceSearch.getOrderByComparator());

		ddmFormInstanceSearch.setResults(results);
	}

	protected void setDDMFormInstanceSearchTotal(
		FormInstanceSearch ddmFormInstanceSearch) {

		int total = _ddmFormInstanceService.searchCount(
			formAdminRequestHelper.getCompanyId(),
			formAdminRequestHelper.getScopeGroupId(), getKeywords());

		ddmFormInstanceSearch.setTotal(total);
	}

	protected final DDMFormAdminRequestHelper formAdminRequestHelper;

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormAdminDisplayContext.class);

	private final AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
		_addDefaultSharedFormLayoutPortalInstanceLifecycleListener;
	private final DDMExporterFactory _ddmExporterFactory;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormFieldTypesJSONSerializer
		_ddmFormFieldTypesJSONSerializer;
	private DDMFormInstance _ddmFormInstance;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private final DDMFormInstanceService _ddmFormInstanceService;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final DDMFormWebConfiguration _ddmFormWebConfiguration;
	private DDMStructure _ddmStructure;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMStructureService _ddmStructureService;
	private String _displayStyle;
	private final JSONFactory _jsonFactory;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StorageEngine _storageEngine;

}