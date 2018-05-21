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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.form.web.internal.instance.lifecycle.AddDefaultSharedFormLayoutPortalInstanceLifecycleListener;
import com.liferay.dynamic.data.mapping.form.web.internal.search.FieldSetSearch;
import com.liferay.dynamic.data.mapping.form.web.internal.search.FieldSetSearchTerms;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormPermission;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.exporter.DDMExporterFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.dynamic.data.mapping.util.comparator.StructureCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class DDMFormAdminFieldSetDisplayContext
	extends DDMFormAdminDisplayContext {

	public DDMFormAdminFieldSetDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
		DDMExporterFactory ddmExporterFactory,
		DDMFormWebConfiguration ddmFormWebConfiguration,
		DDMFormInstanceRecordLocalService formInstanceRecordLocalService,
		DDMFormInstanceService formInstanceService,
		DDMFormFieldTypeServicesTracker formFieldTypeServicesTracker,
		DDMFormFieldTypesJSONSerializer formFieldTypesJSONSerializer,
		DDMFormRenderer formRenderer, DDMFormValuesFactory formValuesFactory,
		DDMFormValuesMerger formValuesMerger,
		DDMStructureLocalService structureLocalService,
		DDMStructureService structureService, JSONFactory jsonFactory,
		StorageEngine storageEngine) {

		super(
			renderRequest, renderResponse,
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
			ddmExporterFactory, ddmFormWebConfiguration,
			formInstanceRecordLocalService, formInstanceService,
			formFieldTypeServicesTracker, formFieldTypesJSONSerializer,
			formRenderer, formValuesFactory, formValuesMerger,
			structureLocalService, structureService, jsonFactory,
			storageEngine);
	}

	public List<DropdownItem> getActionItemsDropdownItems() {
		return new DropdownItemList() {

			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteStructures");
						dropdownItem.setIcon("trash");
						dropdownItem.setLabel(
							LanguageUtil.get(
								formAdminRequestHelper.getRequest(),
								"recycle-bin"));
						dropdownItem.setQuickAction(true);
					});
			}

		};
	}

	public CreationMenu getCreationMenu() {
		if (!isShowAddButton()) {
			return null;
		}

		return new CreationMenu() {
			{
				HttpServletRequest request = PortalUtil.getHttpServletRequest(
					getRenderRequest());
				RenderResponse renderResponse = getRenderResponse();

				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							renderResponse.createRenderURL(), "mvcPath",
							"/admin/edit_element_set.jsp", "redirect",
							PortalUtil.getCurrentURL(request), "groupId",
							String.valueOf(themeDisplay.getScopeGroupId()));

						dropdownItem.setLabel(
							LanguageUtil.get(request, "new-element-set"));
					});
			}
		};
	}

	@Override
	public DDMStructure getDDMStructure() {
		if (_structure != null) {
			return _structure;
		}

		long structureId = ParamUtil.getLong(getRenderRequest(), "structureId");

		if (structureId > 0) {
			try {
				DDMStructureService structureService = getStructureService();

				_structure = structureService.getStructure(structureId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe);
				}
			}
		}

		return _structure;
	}

	@Override
	public String getFormDescription() {
		DDMStructure structure = getDDMStructure();

		if (structure != null) {
			return LocalizationUtil.getLocalization(
				structure.getDescription(), getDefaultLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("description");
	}

	@Override
	public String getFormLocalizedDescription() {
		DDMStructure structure = getDDMStructure();

		JSONFactory jsonFactory = getJSONFactory();

		JSONObject jsonObject = jsonFactory.createJSONObject();

		if (structure == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> descriptionMap = structure.getDescriptionMap();

			for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	@Override
	public String getFormLocalizedName() {
		DDMStructure structure = getDDMStructure();

		JSONFactory jsonFactory = getJSONFactory();

		JSONObject jsonObject = jsonFactory.createJSONObject();

		if (structure == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> nameMap = structure.getNameMap();

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	@Override
	public String getFormName() {
		DDMStructure structure = getDDMStructure();

		if (structure != null) {
			return LocalizationUtil.getLocalization(
				structure.getName(), getDefaultLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("name");
	}

	@Override
	public PortletURL getPortletURL() {
		RenderResponse renderResponse = getRenderResponse();

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));
		portletURL.setParameter("currentTab", "element-set");

		String delta = ParamUtil.getString(getRenderRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
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

	@Override
	public SearchContainer<?> getSearch() {
		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", getDisplayStyle());

		FieldSetSearch fieldSetSearch = new FieldSetSearch(
			getRenderRequest(), portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMStructure> orderByComparator =
			getDDMStructureOrderByComparator(orderByCol, orderByType);

		fieldSetSearch.setOrderByCol(orderByCol);
		fieldSetSearch.setOrderByComparator(orderByComparator);
		fieldSetSearch.setOrderByType(orderByType);

		if (fieldSetSearch.isSearch()) {
			fieldSetSearch.setEmptyResultsMessage("no-element-sets-were-found");
		}
		else {
			fieldSetSearch.setEmptyResultsMessage("there-are-no-element-sets");
		}

		setFieldSetsSearchResults(fieldSetSearch);
		setFieldSetsSearchTotal(fieldSetSearch);

		return fieldSetSearch;
	}

	public String getSearchActionURL() {
		RenderResponse renderResponse = getRenderResponse();

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));
		portletURL.setParameter("currentTab", "element-set");

		return portletURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "structure";
	}

	@Override
	public boolean isShowAddButton() {
		return DDMFormPermission.contains(
			getPermissionChecker(), getScopeGroupId(), "ADD_STRUCTURE");
	}

	protected OrderByComparator<DDMStructure> getDDMStructureOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDMStructure> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new StructureCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new StructureModifiedDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new StructureNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	protected void setFieldSetsSearchResults(FieldSetSearch fieldSetSearch) {
		FieldSetSearchTerms fieldSetSearchTerms =
			(FieldSetSearchTerms)fieldSetSearch.getSearchTerms();

		DDMStructureService structureService = getStructureService();

		List<DDMStructure> results = structureService.search(
			getCompanyId(), new long[] {getScopeGroupId()},
			PortalUtil.getClassNameId(DDMFormInstance.class),
			fieldSetSearchTerms.getKeywords(),
			DDMStructureConstants.TYPE_FRAGMENT, WorkflowConstants.STATUS_ANY,
			fieldSetSearch.getStart(), fieldSetSearch.getEnd(),
			fieldSetSearch.getOrderByComparator());

		fieldSetSearch.setResults(results);
	}

	protected void setFieldSetsSearchTotal(FieldSetSearch fieldSetSearch) {
		FieldSetSearchTerms fieldSetSearchTerms =
			(FieldSetSearchTerms)fieldSetSearch.getSearchTerms();

		DDMStructureService structureService = getStructureService();

		int total = structureService.searchCount(
			getCompanyId(), new long[] {getScopeGroupId()},
			PortalUtil.getClassNameId(DDMFormInstance.class),
			fieldSetSearchTerms.getKeywords(),
			DDMStructureConstants.TYPE_FRAGMENT, WorkflowConstants.STATUS_ANY);

		fieldSetSearch.setTotal(total);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormAdminFieldSetDisplayContext.class);

	private DDMStructure _structure;

}