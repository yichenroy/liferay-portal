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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.search.CPDefinitionIndexer;
import com.liferay.commerce.product.search.CPDefinitionSearcher;
import com.liferay.commerce.product.search.FacetImpl;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.util.FacetFactory;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CPDefinitionHelperImpl implements CPDefinitionHelper {

	@Override
	public BaseModelSearchResult<CPDefinition> getCPDefinitions(
			long companyId, long groupId, String keywords, String filterFields,
			String filterValues, int start, int end, Sort sort)
		throws PortalException {

		List<CPDefinition> cpCPDefinitions = new ArrayList<>();

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, keywords, start, end, sort);

		List<Facet> facets = getFacets(
			filterFields, filterValues, searchContext);

		searchContext.setFacets(facets);

		Hits hits = indexer.search(searchContext);

		Document[] documents = hits.getDocs();

		for (Document document : documents) {
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			cpCPDefinitions.add(_cpDefinitionService.getCPDefinition(classPK));
		}

		return new BaseModelSearchResult<>(cpCPDefinitions, hits.getLength());
	}

	@Override
	public List<Facet> getFacets(
		String filterFields, String filterValues, SearchContext searchContext) {

		List<Facet> facets = new ArrayList<>();

		if (Validator.isNotNull(filterFields) &&
			Validator.isNotNull(filterValues)) {

			Map<String, List<String>> facetMap = new HashMap<>();

			String[] filterFieldsArray = StringUtil.split(filterFields);
			String[] filterValuesArray = StringUtil.split(filterValues);

			List<String> options = new ArrayList<>();

			for (int i = 0; i < filterFieldsArray.length; i++) {
				String key = filterFieldsArray[i];
				String value = filterValuesArray[i];

				if (key.startsWith("OPTION_")) {
					key = key.replace("OPTION_", StringPool.BLANK);

					key = _getIndexFieldName(key);

					options.add(key);
				}

				List<String> facetValues = null;

				if (facetMap.containsKey(key)) {
					facetValues = facetMap.get(key);
				}

				if (facetValues == null) {
					facetValues = new ArrayList<>();
				}

				facetValues.add(value);

				facetMap.put(key, facetValues);
			}

			for (Map.Entry<String, List<String>> entry : facetMap.entrySet()) {
				String fieldName = entry.getKey();

				FacetImpl facet = new FacetImpl(fieldName, searchContext);

				List<String> facetValues = entry.getValue();

				String[] facetValuesArray = ArrayUtil.toStringArray(
					facetValues);

				facet.select(facetValuesArray);

				if (fieldName.equals("assetCategoryIds")) {
					Stream<String> stream = Arrays.stream(facetValuesArray);

					long[] assetCategoryIds = stream.mapToLong(
						GetterUtil::getLong).toArray();

					searchContext.setAssetCategoryIds(assetCategoryIds);
				}

				facets.add(facet);
			}
		}

		return facets;
	}

	@Override
	public String getFriendlyURL(long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		long classNameId = _portal.getClassNameId(CPDefinition.class);

		CPFriendlyURLEntry cpFriendlyURLEntry =
			_cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntry(
				themeDisplay.getScopeGroupId(), classNameId, cpDefinitionId,
				themeDisplay.getLanguageId(), true);

		if (cpFriendlyURLEntry == null) {
			cpFriendlyURLEntry =
				_cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntry(
					themeDisplay.getScopeGroupId(), classNameId, cpDefinitionId,
					LocaleUtil.toLanguageId(
						themeDisplay.getSiteDefaultLocale()),
					true);
		}

		if (cpFriendlyURLEntry == null) {
			if (_log.isInfoEnabled()) {
				_log.info("No friendly URL found for " + cpDefinitionId);
			}

			return StringPool.BLANK;
		}

		Group group = themeDisplay.getScopeGroup();

		String currentSiteURL =
			_portal.getPortalURL(themeDisplay) +
				themeDisplay.getPathFriendlyURLPublic() +
					group.getFriendlyURL();

		return currentSiteURL + CPConstants.SEPARATOR_PRODUCT_URL +
			cpFriendlyURLEntry.getUrlTitle();
	}

	@Override
	public Layout getProductLayout(
			long groupId, boolean privateLayout, long cpDefinitionId)
		throws PortalException {

		String layoutUuid = _cpDefinitionService.getLayoutUuid(cpDefinitionId);

		if (Validator.isNotNull(layoutUuid)) {
			return _layoutLocalService.getLayoutByUuidAndGroupId(
				layoutUuid, groupId, privateLayout);
		}

		long plid = _portal.getPlidFromPortletId(
			groupId, CPPortletKeys.CP_CONTENT_WEB);

		return _layoutLocalService.getLayout(plid);
	}

	public boolean isVisible(long cpDefinitionId) throws PortalException {
		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionId);

		if (!cpDefinition.isPublished()) {
			return false;
		}

		if (!cpDefinition.isApproved()) {
			return false;
		}

		//TODO Permission checking

		return true;
	}

	@Override
	public CPDataSourceResult search(
			long groupId, SearchContext searchContext, CPQuery cpQuery,
			int start, int end)
		throws Exception {

		List<CPCatalogEntry> cpCatalogEntries = new ArrayList<>();

		CPDefinitionSearcher cpDefinitionSearcher = _getCPDefinitionSearcher(
			groupId, searchContext, cpQuery, start, end);

		cpDefinitionSearcher.setCPQuery(cpQuery);

		Hits hits = cpDefinitionSearcher.search(searchContext);

		Document[] documents = hits.getDocs();

		for (Document document : documents) {
			cpCatalogEntries.add(
				_getCPCatalogEntry(document, searchContext.getLocale()));
		}

		return new CPDataSourceResult(cpCatalogEntries, hits.getLength());
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, String keywords, int start, int end,
		Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ENTRY_CLASS_PK, keywords);
		attributes.put(Field.NAME, keywords);
		attributes.put(Field.DESCRIPTION, keywords);
		attributes.put(Field.CONTENT, keywords);
		attributes.put(Field.STATUS, WorkflowConstants.STATUS_ANY);

		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(Field.ENTRY_CLASS_PK);

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	private CPCatalogEntry _getCPCatalogEntry(
		Document document, Locale locale) {

		CPCatalogEntry cpCatalogEntry = new CPCatalogEntry();

		cpCatalogEntry.setCPDefinitionId(
			GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));
		cpCatalogEntry.setDefaultImageFileUrl(
			document.get(CPDefinitionIndexer.FIELD_DEFAULT_IMAGE_FILE_URL));
		cpCatalogEntry.setDescription(document.get(locale, Field.DESCRIPTION));
		cpCatalogEntry.setName(document.get(locale, Field.NAME));
		cpCatalogEntry.setShortDescription(
			document.get(locale, CPDefinitionIndexer.FIELD_SHORT_DESCRIPTION));
		cpCatalogEntry.setUrl(document.get(locale, Field.URL));

		return cpCatalogEntry;
	}

	private CPDefinitionSearcher _getCPDefinitionSearcher(
			long groupId, SearchContext searchContext, CPQuery cpQuery,
			int start, int end)
		throws Exception {

		Indexer<?> searcher = CPDefinitionSearcher.getInstance();

		CPDefinitionSearcher cpDefinitionSearcher =
			(CPDefinitionSearcher)searcher;

		cpDefinitionSearcher.setCPQuery(cpQuery);

		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setScoreEnabled(false);

		searchContext.setSorts(_getSorts(cpQuery, searchContext.getLocale()));
		searchContext.setStart(start);

		return cpDefinitionSearcher;
	}

	private String _getIndexFieldName(String optionKey) {
		return "ATTRIBUTE_" + optionKey + "_VALUES_NAMES";
	}

	private String _getOrderByCol(String sortField, Locale locale) {
		if (sortField.equals("modifiedDate")) {
			sortField = Field.MODIFIED_DATE;
		}
		else if (sortField.equals("name")) {
			sortField = Field.getSortableFieldName(
				"localized_name_".concat(LocaleUtil.toLanguageId(locale)));
		}

		return sortField;
	}

	private Sort _getSort(String orderByType, String sortField, Locale locale) {
		int sortType = _getSortType(sortField);

		return SortFactoryUtil.getSort(
			CPDefinition.class, sortType, _getOrderByCol(sortField, locale),
			orderByType);
	}

	private Sort[] _getSorts(CPQuery cpQuery, Locale locale) {
		Sort sort1 = _getSort(
			cpQuery.getOrderByType1(), cpQuery.getOrderByCol1(), locale);
		Sort sort2 = _getSort(
			cpQuery.getOrderByType2(), cpQuery.getOrderByCol2(), locale);

		return new Sort[] {sort1, sort2};
	}

	private int _getSortType(String fieldType) {
		int sortType = Sort.STRING_TYPE;

		if (fieldType.equals(Field.CREATE_DATE) ||
			fieldType.equals(Field.EXPIRATION_DATE) ||
			fieldType.equals(Field.PUBLISH_DATE) ||
			fieldType.equals("modifiedDate")) {

			sortType = Sort.LONG_TYPE;
		}
		else if (fieldType.equals(Field.PRIORITY)) {
			sortType = Sort.DOUBLE_TYPE;
		}

		return sortType;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionHelperImpl.class);

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

	@Reference
	private FacetFactory _facetFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}