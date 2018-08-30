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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.search.permission.SearchPermissionFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = PreFilterContributorHelper.class)
public class PreFilterContributorHelperImpl
	implements PreFilterContributorHelper {

	@Override
	public void contribute(
		BooleanFilter booleanFilter,
		Map<String, Indexer<?>> entryClassNameIndexerMap,
		SearchContext searchContext) {

		_addPreFilters(booleanFilter, searchContext);

		BooleanFilter preFilterBooleanFilter = new BooleanFilter();

		for (Map.Entry<String, Indexer<?>> entry :
				entryClassNameIndexerMap.entrySet()) {

			String entryClassName = entry.getKey();
			Indexer<?> indexer = entry.getValue();

			preFilterBooleanFilter.add(
				_createPreFilterForEntryClassName(
					entryClassName, indexer, searchContext),
				BooleanClauseOccur.SHOULD);
		}

		if (preFilterBooleanFilter.hasClauses()) {
			booleanFilter.add(preFilterBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		Stream<ModelPreFilterContributor> stream =
			modelPreFilterContributorsHolder.getByModel(
				modelSearchSettings.getClassName());

		stream.forEach(
			modelPreFilterContributor -> modelPreFilterContributor.contribute(
				booleanFilter, modelSearchSettings, searchContext));
	}

	@Reference
	protected ModelPreFilterContributorsHolder modelPreFilterContributorsHolder;

	@Reference
	protected QueryPreFilterContributorsHolder queryPreFilterContributorsHolder;

	@Reference
	protected SearchPermissionChecker searchPermissionChecker;

	@Reference
	protected SearchPermissionFilterContributorsHolder
		searchPermissionFilterContributorsHolder;

	private void _addIndexerProvidedPreFilters(
		BooleanFilter booleanFilter, Indexer<?> indexer,
		SearchContext searchContext) {

		try {
			indexer.postProcessContextBooleanFilter(
				booleanFilter, searchContext);

			for (IndexerPostProcessor indexerPostProcessor :
					indexer.getIndexerPostProcessors()) {

				indexerPostProcessor.postProcessContextBooleanFilter(
					booleanFilter, searchContext);
			}
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private void _addPermissionFilter(
		BooleanFilter booleanFilter, String entryClassName,
		SearchContext searchContext) {

		if (searchContext.getUserId() == 0) {
			return;
		}

		Optional<String> optional = _getParentEntryClassNameOptional(
			entryClassName);

		String permissionedEntryClassName = optional.orElse(entryClassName);

		searchPermissionChecker.getPermissionBooleanFilter(
			searchContext.getCompanyId(), searchContext.getGroupIds(),
			searchContext.getUserId(), permissionedEntryClassName,
			booleanFilter, searchContext);
	}

	private void _addPreFilters(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		Stream<QueryPreFilterContributor> stream =
			queryPreFilterContributorsHolder.getAll();

		stream.forEach(
			queryPreFilterContributor -> queryPreFilterContributor.contribute(
				booleanFilter, searchContext));
	}

	private Filter _createPreFilterForEntryClassName(
		String entryClassName, Indexer<?> indexer,
		SearchContext searchContext) {

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.addTerm(
			Field.ENTRY_CLASS_NAME, entryClassName, BooleanClauseOccur.MUST);

		_addPermissionFilter(booleanFilter, entryClassName, searchContext);

		_addIndexerProvidedPreFilters(booleanFilter, indexer, searchContext);

		ModelSearchSettings modelSearchSettings = _getModelSearchSettings(
			indexer);

		contribute(booleanFilter, modelSearchSettings, searchContext);

		return booleanFilter;
	}

	private ModelSearchSettings _getModelSearchSettings(Indexer indexer) {
		ModelSearchSettingsImpl modelSearchSettingsImpl =
			new ModelSearchSettingsImpl(indexer.getClassName());

		modelSearchSettingsImpl.setStagingAware(indexer.isStagingAware());

		return modelSearchSettingsImpl;
	}

	private Optional<String> _getParentEntryClassNameOptional(
		String entryClassName) {

		Stream<SearchPermissionFilterContributor> stream =
			searchPermissionFilterContributorsHolder.getAll();

		List<SearchPermissionFilterContributor> list = stream.collect(
			Collectors.toList());

		for (SearchPermissionFilterContributor
				searchPermissionFilterContributor : list) {

			Optional<String> parentEntryClassNameOptional =
				searchPermissionFilterContributor.
					getParentEntryClassNameOptional(entryClassName);

			if ((parentEntryClassNameOptional != null) &&
				parentEntryClassNameOptional.isPresent()) {

				return parentEntryClassNameOptional;
			}
		}

		return Optional.empty();
	}

}