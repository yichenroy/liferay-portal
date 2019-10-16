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

package com.liferay.commerce.product.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.service.CPOptionLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Arquillian.class)
@Sync
public class CPInstanceStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new LinkedHashMap<>();

		long groupId = group.getGroupId();

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(groupId);
		CPOption cpOption = CPTestUtil.addCPOption(groupId);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			CPTestUtil.addCPDefinitionOptionRel(
				groupId, cpDefinition.getCPDefinitionId(),
				cpOption.getCPOptionId());

		for (int i = 0; i < CP_DEFINITION_OPTION_VALUE_RELS_COUNT; i++) {
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				CPTestUtil.addCPDefinitionOptionValueRel(
					groupId,
					cpDefinitionOptionRel.getCPDefinitionOptionRelId());

			addDependentStagedModel(
				dependentStagedModelsMap, CPDefinitionOptionValueRel.class,
				cpDefinitionOptionValueRel);
		}

		addDependentStagedModel(
			dependentStagedModelsMap, CPDefinitionOptionRel.class,
			cpDefinitionOptionRel);
		addDependentStagedModel(
			dependentStagedModelsMap, CPDefinition.class, cpDefinition);
		addDependentStagedModel(
			dependentStagedModelsMap, CPOption.class, cpOption);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> cpDefinitionDependentStagedModels =
			dependentStagedModelsMap.get(CPDefinition.class.getSimpleName());

		CPDefinition cpDefinition =
			(CPDefinition)cpDefinitionDependentStagedModels.get(0);

		List<StagedModel> cpDefinitionOptionValueRelDependentStagedModels =
			dependentStagedModelsMap.get(
				CPDefinitionOptionValueRel.class.getSimpleName());

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			new ArrayList<>(
				cpDefinitionOptionValueRelDependentStagedModels.size());

		for (StagedModel stagedModel :
				cpDefinitionOptionValueRelDependentStagedModels) {

			cpDefinitionOptionValueRels.add(
				(CPDefinitionOptionValueRel)stagedModel);
		}

		return CPTestUtil.addCPInstance(
			group.getGroupId(), cpDefinition.getCPDefinitionId(),
			cpDefinitionOptionValueRels);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CPInstanceLocalServiceUtil.getCPInstanceByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CPInstance.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> cpDefinitionDependentStagedModels =
			dependentStagedModelsMap.get(CPDefinition.class.getSimpleName());

		Assert.assertEquals(
			cpDefinitionDependentStagedModels.toString(), 1,
			cpDefinitionDependentStagedModels.size());

		CPDefinition cpDefinition =
			(CPDefinition)cpDefinitionDependentStagedModels.get(0);

		CPDefinitionLocalServiceUtil.getCPDefinitionByUuidAndGroupId(
			cpDefinition.getUuid(), group.getGroupId());

		List<StagedModel> cpOptionDependentStagedModels =
			dependentStagedModelsMap.get(CPOption.class.getSimpleName());

		Assert.assertEquals(
			cpOptionDependentStagedModels.toString(), 1,
			cpOptionDependentStagedModels.size());

		CPOption cpOption = (CPOption)cpOptionDependentStagedModels.get(0);

		CPOptionLocalServiceUtil.getCPOptionByUuidAndGroupId(
			cpOption.getUuid(), group.getGroupId());

		List<StagedModel> cpDefinitionOptionRelDependentStagedModels =
			dependentStagedModelsMap.get(
				CPDefinitionOptionRel.class.getSimpleName());

		Assert.assertEquals(
			cpDefinitionOptionRelDependentStagedModels.toString(), 1,
			cpDefinitionOptionRelDependentStagedModels.size());

		CPDefinitionOptionRel cpDefinitionOptionRel =
			(CPDefinitionOptionRel)
				cpDefinitionOptionRelDependentStagedModels.get(0);

		CPDefinitionOptionRelLocalServiceUtil.
			getCPDefinitionOptionRelByUuidAndGroupId(
				cpDefinitionOptionRel.getUuid(), group.getGroupId());

		List<StagedModel> cpDefinitionOptionValueRelDependentStagedModels =
			dependentStagedModelsMap.get(
				CPDefinitionOptionValueRel.class.getSimpleName());

		Assert.assertEquals(
			cpDefinitionOptionValueRelDependentStagedModels.toString(),
			CP_DEFINITION_OPTION_VALUE_RELS_COUNT,
			cpDefinitionOptionValueRelDependentStagedModels.size());

		for (StagedModel stagedModel :
				cpDefinitionOptionValueRelDependentStagedModels) {

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				(CPDefinitionOptionValueRel)stagedModel;

			CPDefinitionOptionValueRelLocalServiceUtil.
				getCPDefinitionOptionValueRelByUuidAndGroupId(
					cpDefinitionOptionValueRel.getUuid(), group.getGroupId());
		}
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		CPInstance cpInstance = (CPInstance)stagedModel;
		CPInstance importedCPInstance = (CPInstance)importedStagedModel;

		Assert.assertEquals(cpInstance.getSku(), importedCPInstance.getSku());
		CPTestUtil.assertDateEquals(
			cpInstance.getDisplayDate(), importedCPInstance.getDisplayDate());
		CPTestUtil.assertDateEquals(
			cpInstance.getExpirationDate(),
			importedCPInstance.getExpirationDate());
	}

	protected static final int CP_DEFINITION_OPTION_VALUE_RELS_COUNT = 3;

}