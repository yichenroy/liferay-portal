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

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.search.CPAttachmentFileEntryIndexer;
import com.liferay.commerce.product.search.CPInstanceIndexer;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CPInstanceHelperImpl implements CPInstanceHelper {

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long cpDefinitionId, Locale locale, String serializedDDMFormValues,
			int type)
		throws Exception {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries = new ArrayList<>();

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionId);

		long cpDefinitionClassNameId = _portal.getClassNameId(
			CPDefinition.class);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			serializedDDMFormValues);

		Indexer<CPAttachmentFileEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CPAttachmentFileEntry.class);

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(
			CPAttachmentFileEntryIndexer.FIELD_RELATED_ENTITY_CLASS_NAME_ID,
			cpDefinitionClassNameId);
		attributes.put(
			CPAttachmentFileEntryIndexer.FIELD_RELATED_ENTITY_CLASS_PK,
			cpDefinitionId);
		attributes.put(Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		attributes.put(Field.TYPE, type);

		List<String> optionsKeys = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString("key");

			String fieldName = "ATTRIBUTE_" + key + "_VALUES_IDS";

			optionsKeys.add(fieldName);

			JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray(
				jsonObject.getString("value"));

			String[] values = new String[valuesJSONArray.length()];

			for (int ii = 0; ii < valuesJSONArray.length(); ii++) {
				values[ii] = valuesJSONArray.getString(ii);
			}

			attributes.put(fieldName, values);
		}

		attributes.put("OPTIONS", ArrayUtil.toStringArray(optionsKeys));

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(cpDefinition.getCompanyId());
		searchContext.setGroupIds(new long[] {cpDefinition.getGroupId()});

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Sort prioitySort = SortFactoryUtil.create(Field.PRIORITY, true);

		searchContext.setSorts(prioitySort);

		queryConfig.addSelectedFieldNames(Field.ENTRY_CLASS_PK);

		Hits hits = indexer.search(searchContext);

		Document[] documents = hits.getDocs();

		for (Document document : documents) {
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			cpAttachmentFileEntries.add(
				_cpAttachmentFileEntryService.getCPAttachmentFileEntry(
					classPK));
		}

		return cpAttachmentFileEntries;
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRel(
			long cpDefinitionId, String optionFieldName,
			Map<String, String> optionMap)
		throws Exception {

		Indexer<CPInstance> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPInstance.class);

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(
			CPInstanceIndexer.FIELD_CP_DEFINITION_ID, cpDefinitionId);
		attributes.put(Field.STATUS, WorkflowConstants.STATUS_APPROVED);

		List<String> optionsKeys = new ArrayList<>();

		for (Map.Entry<String, String> optionEntry : optionMap.entrySet()) {
			String fieldName =
				"ATTRIBUTE_" + optionEntry.getKey() + "_VALUE_ID";

			optionsKeys.add(fieldName);

			attributes.put(fieldName, optionEntry.getValue());
		}

		attributes.put("OPTIONS", ArrayUtil.toStringArray(optionsKeys));

		searchContext.setAttributes(attributes);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.addSelectedFieldNames(optionFieldName);

		searchContext.setQueryConfig(queryConfig);

		Hits hits = indexer.search(searchContext);

		Document[] documents = hits.getDocs();

		List<Long> cpDefinitionOptionValueRelIsList = new ArrayList<>();

		for (Document document : documents) {
			long classPK = GetterUtil.getLong(document.get(optionFieldName));

			if (classPK > 0) {
				cpDefinitionOptionValueRelIsList.add(classPK);
			}
		}

		Stream<Long> stream = cpDefinitionOptionValueRelIsList.stream();

		long[] cpDefinitionOptionValueRelIds = stream.mapToLong(
			l -> l
		).toArray();

		return _cpDefinitionOptionValueRelLocalService.
			getCPDefinitionOptionValueRels(cpDefinitionOptionValueRelIds);
	}

	@Override
	public DDMForm getDDMForm(
			long cpDefinitionId, Locale locale, boolean required)
		throws PortalException {

		DDMForm ddmForm = new DDMForm();

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			_cpDefinitionOptionRelLocalService.
				getSkuContributorCPDefinitionOptionRels(cpDefinitionId);

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

			if (Validator.isNull(
					cpDefinitionOptionRel.getDDMFormFieldTypeName())) {

				continue;
			}

			CPOption cpOption = cpDefinitionOptionRel.getCPOption();

			DDMFormField ddmFormField = new DDMFormField(
				String.valueOf(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId()),
				cpDefinitionOptionRel.getDDMFormFieldTypeName());

			ddmFormField.setProperty("cpOption", cpOption);

			if (!cpDefinitionOptionValueRels.isEmpty()) {
				DDMFormFieldOptions ddmFormFieldOptions =
					new DDMFormFieldOptions();

				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
						cpDefinitionOptionValueRels) {

					String optionLabel = String.valueOf(
						cpDefinitionOptionValueRel.
							getCPDefinitionOptionValueRelId());

					ddmFormFieldOptions.addOptionLabel(
						optionLabel, locale,
						cpDefinitionOptionValueRel.getTitle(locale));
				}

				ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
			}

			LocalizedValue localizedValue = new LocalizedValue(locale);

			localizedValue.addString(
				locale, cpDefinitionOptionRel.getTitle(locale));

			ddmFormField.setLabel(localizedValue);

			ddmFormField.setRequired(required);

			ddmForm.addDDMFormField(ddmFormField);
		}

		ddmForm.addDDMFormRule(createDDMFormRule(ddmForm, cpDefinitionId));
		ddmForm.addAvailableLocale(locale);
		ddmForm.setDefaultLocale(locale);

		return ddmForm;
	}

	@Override
	public DDMFormValues getDDMFormValues(
		long cpDefinitionId, Locale locale, String serializedDDMFormValues) {

		if (Validator.isNotNull(serializedDDMFormValues)) {
			try {
				DDMForm ddmForm = getDDMForm(cpDefinitionId, locale, true);

				return DDMUtil.getDDMFormValues(
					ddmForm, serializedDDMFormValues);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return null;
	}

	@Override
	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			parseJSONString(String json)
		throws PortalException {

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelListMap = new HashMap<>();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long cpDefinitionOptionRelId = GetterUtil.getLong(
				jsonObject.getString("key"));
			JSONArray valueJSONArray = jsonObject.getJSONArray("value");

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

			if (cpDefinitionOptionRel == null) {
				continue;
			}

			for (int j = 0; j < valueJSONArray.length(); j++) {
				long cpDefinitionOptionValueRelId = GetterUtil.getLong(
					valueJSONArray.getString(j));

				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelLocalService.
						fetchCPDefinitionOptionValueRel(
							cpDefinitionOptionValueRelId);

				if (cpDefinitionOptionValueRel == null) {
					continue;
				}

				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
					cpDefinitionOptionRelListMap.get(cpDefinitionOptionRel);

				if (cpDefinitionOptionValueRels == null) {
					cpDefinitionOptionValueRels = new ArrayList<>();

					cpDefinitionOptionRelListMap.put(
						cpDefinitionOptionRel, cpDefinitionOptionValueRels);
				}

				cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);
			}
		}

		return cpDefinitionOptionRelListMap;
	}

	@Override
	public String render(
			long cpDefinitionId, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException {

		return render(
			cpDefinitionId, null, true, renderRequest, renderResponse);
	}

	@Override
	public String render(
			long cpDefinitionId, String json, boolean required,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		Locale locale = _portal.getLocale(httpServletRequest);

		DDMForm ddmForm = getDDMForm(cpDefinitionId, locale, required);

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId(String.valueOf(cpDefinitionId));
		ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(httpServletResponse);
		ddmFormRenderingContext.setLocale(locale);
		ddmFormRenderingContext.setPortletNamespace(
			renderResponse.getNamespace());

		if (Validator.isNotNull(json)) {
			DDMFormValues ddmFormValues = _ddmFormValuesHelper.deserialize(
				ddmForm, json, locale);

			if (ddmFormValues != null) {
				ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
			}
		}

		return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
	}

	protected DDMFormRule createDDMFormRule(
		DDMForm ddmForm, long cpDefinitionId) {

		String action = createDDMFormRuleAction(ddmForm, cpDefinitionId);
		String condition = createDDMFormRuleCondition(ddmForm);

		return new DDMFormRule(condition, action);
	}

	protected String createDDMFormRuleAction(
		DDMForm ddmForm, long cpDefinitionId) {

		/*
		 * The action is a call function. Example:
		 *
		 *call(
		 *	'getCPInstanceOptionsValues',
		 *	 concat(
		 *		'cpDefinitionId=56698', ';','56703=', getValue('56703'), ';',
		 *		'56706=', getValue('56706')),
		 *	'56703=color;56706=size'
		 *)
		 */

		String callFunctionStatement =
			"call('getCPInstanceOptionsValues', concat(%s), '%s')";

		return String.format(
			callFunctionStatement,
			createDDMFormRuleInputMapping(ddmForm, cpDefinitionId),
			createDDMFormRuleOutputMapping(ddmForm));
	}

	protected String createDDMFormRuleCondition(DDMForm ddmForm) {

		/*
		 * The rule action will contains a 'notEmpty' statement for each field
		 * using 'OR' operator.
		 * Ex: not(isEmpty(getValue('{sizeFieldName}')))
		 *  	OR not(isEmpty(getValue('{colorFieldName}')))
		 */

		String notEmptyStatement = "not(isEmpty(getValue('%s')))";

		Stream<DDMFormField> stream = ddmForm.getDDMFormFields().stream();

		Stream<String> notEmptyStatementStream = stream.map(
			field -> {
				return String.format(notEmptyStatement, field.getName());
			});

		return notEmptyStatementStream.collect(Collectors.joining(" OR "));
	}

	protected String createDDMFormRuleInputMapping(
		DDMForm ddmForm, long cpDefinitionId) {

		/*
		 * The input information will be transformed in parameter request of
		 *  DDMDataProviderRequest class and it'll be accessible in the data
		 *  provider implementation
		 * */

		String inputMappingStatement = "'%s=', getValue('%s')";
		String delimiter = ", ';',";

		Stream<DDMFormField> stream = ddmForm.getDDMFormFields().stream();

		Stream<String> inputMappingStatementStream = stream.map(
			field -> {
				return String.format(
					inputMappingStatement, field.getName(), field.getName());
			});

		inputMappingStatementStream = Stream.concat(
			Stream.of(
				String.format(
					"'cpDefinitionId=%s'", String.valueOf(cpDefinitionId))),
			inputMappingStatementStream);

		return inputMappingStatementStream.collect(
			Collectors.joining(delimiter));
	}

	protected String createDDMFormRuleOutputMapping(DDMForm ddmForm) {
		String outputMappingStatement = "%s=%s";

		Stream<DDMFormField> stream = ddmForm.getDDMFormFields().stream();

		Stream<String> outputMappingStatementStream = stream.map(
			field -> {
				return String.format(
					outputMappingStatement, field.getName(), field.getName());
			});

		return outputMappingStatementStream.collect(
			Collectors.joining(StringPool.SEMICOLON));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceHelperImpl.class);

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private DDMFormValuesHelper _ddmFormValuesHelper;

	@Reference
	private Portal _portal;

}