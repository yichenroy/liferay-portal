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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.internal.util.CPDefinitionsPortletUtil;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.*;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
<<<<<<< HEAD
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
=======
import com.liferay.portal.kernel.util.*;
>>>>>>> LPS-79926 Implement commercd management toolbar in product definitions
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.taglib.util.CustomAttributesUtil;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPDefinitionsDisplayContext
	extends BaseCPDefinitionsSearchContainerDisplayContext<CPDefinition> {

	public CPDefinitionsDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionHelper cpDefinitionHelper,
			CPDefinitionService cpDefinitionService, ItemSelector itemSelector)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CPDefinition.class.getSimpleName());

		setDefaultOrderByType("desc");

		_cpDefinitionHelper = cpDefinitionHelper;
		_cpDefinitionService = cpDefinitionService;
		_itemSelector = itemSelector;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList(httpServletRequest) {
			{
				ThemeDisplay themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				RenderResponse renderResponse =
						(RenderResponse)httpServletRequest.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

				add(
						SafeConsumer.ignore(
								dropdownItem -> {
									dropdownItem.setHref(
											"javascript:" +
													renderResponse.getNamespace() +
													"deleteCPDefinitions();");

									boolean trashEnabled = TrashUtil.isTrashEnabled(
											themeDisplay.getScopeGroupId());

									dropdownItem.setIcon(
											trashEnabled ? "trash" : "times");
									dropdownItem.setLabel(
											trashEnabled ? "recycle-bin" : "delete");

									dropdownItem.setQuickAction(true);
								}));
			}
		};
	}

	public String getCategorySelectorURL(String eventName) throws Exception {
		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, AssetCategory.class.getName(),
			PortletProvider.Action.BROWSE);

		if (portletURL == null) {
			return null;
		}

		portletURL.setParameter("eventName", eventName);
		portletURL.setParameter("selectedCategories", "{selectedCategories}");
		portletURL.setParameter("singleSelect", "{singleSelect}");
		portletURL.setParameter("vocabularyIds", "{vocabularyIds}");
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public String getClearResultsURL() throws PortalException {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() throws PortalException {
		return new CreationMenu(httpServletRequest) {
			{
				ThemeDisplay themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				RenderResponse renderResponse =
						(RenderResponse)httpServletRequest.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);


				for (CPType curCPType : getCPTypes()) {
					addPrimaryDropdownItem(
							dropdownItem -> {
								dropdownItem.setHref(renderResponse.createRenderURL(),
										Constants.CMD, Constants.ADD, "mvcRenderCommandName",
										"editProductDefinition", "backURL", PortalUtil.getCurrentCompleteURL(httpServletRequest),
										"productTypeName", curCPType.getName(), "toolbarItem", "view-product-definition-details");
								dropdownItem.setLabel(curCPType.getLabel(themeDisplay.getLocale()));
							}
					);
				}
			}
		};
	}

	public String[] getDisplayViews() {
		return new String[] {"icon", "descriptive", "list"};
	}

	public String getItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "selectDisplayPage",
			layoutItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public String getLayoutBreadcrumb(Layout layout) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(httpServletRequest, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(httpServletRequest, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
	}

	public String getLayoutUuid() throws PortalException {
		long cpDefinitionId = getCPDefinitionId();

		if (cpDefinitionId <= 0) {
			return null;
		}

		return _cpDefinitionService.getLayoutUuid(cpDefinitionId);
	}

	@Override
	public List<ManagementBarFilterItem> getManagementBarStatusFilterItems()
		throws PortalException, PortletException {

		List<ManagementBarFilterItem> managementBarFilterItems =
			super.getManagementBarStatusFilterItems();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		int workflowDefinitionLinksCount =
			WorkflowDefinitionLinkLocalServiceUtil.
				getWorkflowDefinitionLinksCount(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					CPDefinition.class.getName());

		if (workflowDefinitionLinksCount == 0) {
			workflowDefinitionLinksCount =
				WorkflowDefinitionLinkLocalServiceUtil.
					getWorkflowDefinitionLinksCount(
						themeDisplay.getCompanyId(),
						WorkflowConstants.DEFAULT_GROUP_ID,
						CPDefinition.class.getName());
		}

		if (workflowDefinitionLinksCount > 0) {
			managementBarFilterItems.add(
				getManagementBarFilterItem(WorkflowConstants.STATUS_PENDING));
			managementBarFilterItems.add(
				getManagementBarFilterItem(WorkflowConstants.STATUS_DENIED));
		}

		return managementBarFilterItems;
	}

	public List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList(httpServletRequest) {
			{
				addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
									new DropdownItemList(httpServletRequest) {
										{
											for (String orderColumn : getOrderColumns()) {
												add(
														SafeConsumer.ignore(
																dropdownItem -> {
																	dropdownItem.setActive(orderColumn.equals(getOrderByCol()));
																	dropdownItem.setHref(getPortletURL(), "orderByCol", orderColumn);
																	dropdownItem.setLabel(orderColumn);
																}));
											}

										}
									});
							dropdownGroupItem.setLabel("order-by");
						});
			}
		};
	}

	public String[] getOrderColumns() {
		return new String[] {"display-date", "modified-date", "title"};
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		String filterFields = ParamUtil.getString(
			httpServletRequest, "filterFields");

		if (Validator.isNotNull(filterFields)) {
			portletURL.setParameter("filterFields", filterFields);
		}

		String filtersLabels = ParamUtil.getString(
			httpServletRequest, "filtersLabels");

		if (Validator.isNotNull(filtersLabels)) {
			portletURL.setParameter("filtersLabels", filtersLabels);
		}

		String filtersValues = ParamUtil.getString(
			httpServletRequest, "filtersValues");

		if (Validator.isNotNull(filtersValues)) {
			portletURL.setParameter("filtersValues", filtersValues);
		}

		return portletURL;
	}

	public String getProductURL(CPDefinition cpDefinition)
		throws PortalException {

		if (cpDefinition == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _cpDefinitionHelper.getFriendlyURL(
			cpDefinition.getCPDefinitionId(), themeDisplay);
	}

	@Override
	public SearchContainer<CPDefinition> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CPDefinition> orderByComparator =
			CPDefinitionsPortletUtil.getCPDefinitionOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CPDefinitionsPortletUtil.getCPDefinitionSort(
				getOrderByCol(), getOrderByType());

			String filterFields = ParamUtil.getString(
				httpServletRequest, "filterFields");

			String filtersValues = ParamUtil.getString(
				httpServletRequest, "filtersValues");

			BaseModelSearchResult<CPDefinition>
				cpDefinitionBaseModelSearchResult =
					_cpDefinitionHelper.getCPDefinitions(
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId(), getKeywords(),
						filterFields, filtersValues, searchContainer.getStart(),
						searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				cpDefinitionBaseModelSearchResult.getLength());
			searchContainer.setResults(
				cpDefinitionBaseModelSearchResult.getBaseModels());
		}
		else {
			List<CPDefinition> cpDefinitions =
				CPDefinitionLocalServiceUtil.getCPDefinitions(
					themeDisplay.getScopeGroupId(),
					WorkflowConstants.STATUS_IN_TRASH,
					searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setTotal(cpDefinitions.size());
			searchContainer.setResults(cpDefinitions);
		}

		return searchContainer;
	}

	public String getSortingURL() throws PortalException {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
				"orderByType",
				Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	public String getUrlTitleMapAsXML() throws PortalException {
		long cpDefinitionId = getCPDefinitionId();

		if (cpDefinitionId <= 0) {
			return StringPool.BLANK;
		}

		return _cpDefinitionService.getUrlTitleMapAsXML(cpDefinitionId);
	}

	public List<ViewTypeItem> getViewTypeItems() throws PortalException {
		return new ViewTypeItemList(
				httpServletRequest, getPortletURL(), getDisplayStyle()) {

			{
				if (ArrayUtil.contains(getDisplayViews(), "icon")) {
					addCardViewTypeItem();
				}

				if (ArrayUtil.contains(getDisplayViews(), "descriptive")) {
					addListViewTypeItem();
				}

				if (ArrayUtil.contains(getDisplayViews(), "list")) {
					addTableViewTypeItem();
				}
			}
		};
	}

	public String getVocabularyIds() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				themeDisplay.getScopeGroupId());

		return ListUtil.toString(
			vocabularies, AssetVocabulary.VOCABULARY_ID_ACCESSOR);
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return CustomAttributesUtil.hasCustomAttributes(
			themeDisplay.getCompanyId(), CPDefinition.class.getName(),
			getCPDefinitionId(), null);
	}

	private final CPDefinitionHelper _cpDefinitionHelper;
	private final CPDefinitionService _cpDefinitionService;
	private final ItemSelector _itemSelector;

}