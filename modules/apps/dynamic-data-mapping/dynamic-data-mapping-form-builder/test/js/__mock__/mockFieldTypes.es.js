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

export default [
	{
		description: 'Single line or multiline text area.',
		group: 'basic',
		icon: 'text',
		javaScriptClass: 'Liferay.DDM.Renderer.Field',
		javaScriptModule: 'liferay-ddm-form-renderer-field',
		label: 'Text',
		name: 'text',
		settingsContext: {
			containerId: 'settings',
			currentPage: '1',
			editingLanguageId: 'en_US',
			evaluatorURL: '/o/dynamic-data-mapping-form-context-provider/',
			groupId: 0,
			pages: [
				{
					enabled: true,
					localizedDescription: {},
					localizedTitle: {
						en_US: 'Basic',
					},
					rows: [
						{
							columns: [
								{
									fields: [
										{
											autocompleteEnabled: false,
											dataType: 'string',
											dir: 'ltr',
											displayStyle: 'singleline',
											enabled: true,
											errorMessage: '',
											fieldName: 'label',
											instanceId: '8pq3ebts',
											label: 'Label',
											locale: 'en_US',
											localizable: true,
											localizedValue: {
												en_US: '',
											},
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$label$8pq3ebts$0$$en_US',
											options: [],
											placeholder: 'Enter a field label.',
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											tip: '',
											tooltip:
												'Enter a descriptive field label that guides users to enter the information you want.',
											type: 'text',
											valid: true,
											value: '',
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											autocompleteEnabled: false,
											dataType: 'string',
											dir: 'ltr',
											displayStyle: 'singleline',
											enabled: true,
											errorMessage: '',
											fieldName: 'placeholder',
											instanceId: 'zmV8qWxR',
											label: 'Placeholder Text',
											locale: 'en_US',
											localizable: true,
											localizedValue: {
												en_US: '',
											},
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$placeholder$zmV8qWxR$0$$en_US',
											options: [],
											placeholder: '',
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											tip: '',
											tooltip:
												'Enter text that assists the user, but is not submitted as a field value.',
											type: 'text',
											valid: true,
											value: '',
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											autocompleteEnabled: false,
											dataType: 'string',
											dir: 'ltr',
											displayStyle: 'singleline',
											enabled: true,
											errorMessage: '',
											fieldName: 'tip',
											instanceId: 'jJZMaLY1',
											label: 'Help Text',
											locale: 'en_US',
											localizable: true,
											localizedValue: {
												en_US: '',
											},
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$tip$jJZMaLY1$0$$en_US',
											options: [],
											placeholder: '',
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											tip: '',
											tooltip:
												'Add a comment to help users understand the field label.',
											type: 'text',
											valid: true,
											value: '',
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											dataType: 'string',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											evaluable: true,
											fieldName: 'displayStyle',
											inline: false,
											instanceId: 'BBfGojRv',
											label: 'Field Type',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$displayStyle$BBfGojRv$0$$en_US',
											options: [
												{
													label: 'Single Line',
													value: 'singleline',
												},
												{
													label: 'Multiple Lines',
													value: 'multiline',
												},
											],
											predefinedValue: 'singleline',
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											tip: '',
											type: 'radio',
											valid: true,
											value: 'singleline',
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											dataType: 'boolean',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											fieldName: 'required',
											instanceId: 'vCcPZTUj',
											label: 'Required Field',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$required$vCcPZTUj$0$$en_US',
											options: [],
											predefinedValue: false,
											readOnly: false,
											repeatable: false,
											required: false,
											showAsSwitcher: true,
											showLabel: true,
											tip: '',
											type: 'checkbox',
											valid: true,
											value: false,
											visibilityExpression: 'TRUE',
											visible: true,
										},
									],
									size: 12,
								},
							],
						},
					],
					showRequiredFieldsWarning: false,
					title: 'Basic',
				},
				{
					enabled: true,
					localizedDescription: {},
					localizedTitle: {
						en_US: 'Advanced',
					},
					rows: [
						{
							columns: [
								{
									fields: [
										{
											autocompleteEnabled: false,
											dataType: 'string',
											dir: 'ltr',
											displayStyle: 'singleline',
											enabled: true,
											errorMessage:
												'This field is required.',
											evaluable: true,
											fieldName: 'name',
											instanceId: '5NHpep5F',
											label: 'Field Name',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$name$5NHpep5F$0$$en_US',
											options: [],
											placeholder: '',
											readOnly: false,
											repeatable: false,
											required: true,
											showLabel: true,
											tip: '',
											tooltip: '',
											type: 'text',
											valid: false,
											value: '',
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											autocompleteEnabled: false,
											dataType: 'string',
											dir: 'ltr',
											displayStyle: 'singleline',
											enabled: true,
											errorMessage: '',
											fieldName: 'predefinedValue',
											instanceId: 'S6iXqRJS',
											label: 'Predefined Value',
											locale: 'en_US',
											localizable: true,
											localizedValue: {
												en_US: '',
											},
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$predefinedValue$S6iXqRJS$0$$en_US',
											options: [],
											placeholder:
												'Enter a default value.',
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											tip: '',
											tooltip:
												'Enter a default value that is submitted if no other value is entered.',
											type: 'text',
											valid: true,
											value: '',
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											autocompleteEnabled: false,
											dataType: 'string',
											dir: 'ltr',
											displayStyle: 'singleline',
											enabled: true,
											errorMessage: '',
											fieldName: 'visibilityExpression',
											instanceId: 'ClIWK0Ma',
											label:
												'Field Visibility Expression',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$visibilityExpression$ClIWK0Ma$0$$en_US',
											options: [],
											placeholder:
												'equals(Country, "US")',
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											tip: '',
											tooltip:
												'Write a condition expression to control whether this field is displayed.',
											type: 'text',
											valid: true,
											value: '',
											visibilityExpression: 'FALSE',
											visible: false,
										},
										{
											autocompleteEnabled: false,
											dataType: 'string',
											dir: 'ltr',
											displayStyle: 'singleline',
											enabled: true,
											errorMessage: '',
											fieldName: 'fieldNamespace',
											instanceId: 'tYtvfUOf',
											label: '',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$fieldNamespace$tYtvfUOf$0$$en_US',
											options: [],
											placeholder: '',
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											tip: '',
											tooltip: '',
											type: 'text',
											valid: true,
											value: '',
											visibilityExpression: 'FALSE',
											visible: false,
										},
										{
											dataType: 'string',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											fieldName: 'indexType',
											inline: false,
											instanceId: 'obGPaKnj',
											label: 'Searchable',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$indexType$obGPaKnj$0$$en_US',
											options: [
												{
													label: 'Disable',
													value: 'none',
												},
												{
													label: 'Keyword',
													value: 'keyword',
												},
												{
													label: 'Text',
													value: 'text',
												},
											],
											predefinedValue: 'keyword',
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											tip: '',
											type: 'radio',
											valid: true,
											value: 'keyword',
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											dataType: 'boolean',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											fieldName: 'localizable',
											instanceId: 'wPI1XOin',
											label: 'Localizable',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$localizable$wPI1XOin$0$$en_US',
											options: [],
											predefinedValue: true,
											readOnly: false,
											repeatable: false,
											required: false,
											showAsSwitcher: false,
											showLabel: true,
											tip: '',
											type: 'checkbox',
											valid: true,
											value: true,
											visibilityExpression: 'FALSE',
											visible: false,
										},
										{
											dataType: 'boolean',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											fieldName: 'readOnly',
											instanceId: 'gjoyDWXU',
											label: 'Read Only',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$readOnly$gjoyDWXU$0$$en_US',
											options: [],
											predefinedValue: false,
											readOnly: false,
											repeatable: false,
											required: false,
											showAsSwitcher: false,
											showLabel: true,
											tip: '',
											type: 'checkbox',
											valid: true,
											value: false,
											visibilityExpression: 'FALSE',
											visible: false,
										},
										{
											autocompleteEnabled: false,
											dataType: 'string',
											dir: 'ltr',
											displayStyle: 'singleline',
											enabled: true,
											errorMessage: '',
											evaluable: true,
											fieldName: 'dataType',
											instanceId: 'uUkcnbE5',
											label: '',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$dataType$uUkcnbE5$0$$en_US',
											options: [],
											placeholder: '',
											predefinedValue: 'string',
											readOnly: false,
											repeatable: false,
											required: true,
											showLabel: true,
											tip: '',
											tooltip: '',
											type: 'text',
											valid: true,
											value: 'string',
											visibilityExpression: 'FALSE',
											visible: false,
										},
										{
											autocompleteEnabled: false,
											dataType: 'string',
											dir: 'ltr',
											displayStyle: 'singleline',
											enabled: true,
											errorMessage: '',
											evaluable: true,
											fieldName: 'type',
											instanceId: 'P3DoEVz9',
											label: '',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$type$P3DoEVz9$0$$en_US',
											options: [],
											placeholder: '',
											readOnly: false,
											repeatable: false,
											required: true,
											showLabel: true,
											tip: '',
											tooltip: '',
											type: 'text',
											valid: true,
											value: '',
											visibilityExpression: 'FALSE',
											visible: false,
										},
										{
											dataType: 'boolean',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											fieldName: 'showLabel',
											instanceId: 'wcv4ym60',
											label: 'Show Label',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$showLabel$wcv4ym60$0$$en_US',
											options: [],
											predefinedValue: true,
											readOnly: false,
											repeatable: false,
											required: false,
											showAsSwitcher: true,
											showLabel: true,
											tip: '',
											type: 'checkbox',
											valid: true,
											value: true,
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											dataType: 'boolean',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											fieldName: 'repeatable',
											instanceId: 'pVXy89Ok',
											label: 'Repeatable',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$repeatable$pVXy89Ok$0$$en_US',
											options: [],
											predefinedValue: false,
											readOnly: false,
											repeatable: false,
											required: false,
											showAsSwitcher: true,
											showLabel: true,
											tip: '',
											type: 'checkbox',
											valid: true,
											value: false,
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											dataType: 'string',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											evaluable: true,
											fieldName: 'validation',
											instanceId: 'hJEbyIqR',
											label: 'Validation',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$validation$hJEbyIqR$0$$en_US',
											options: [],
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											tip: '',
											type: 'validation',
											valid: true,
											value: {
												errorMessage: {
													JSONObject: {},
												},
												expression: {
													JSONObject: {},
												},
												parameter: {
													JSONObject: {},
												},
											},
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											autocompleteEnabled: false,
											dataType: 'string',
											dir: 'ltr',
											displayStyle: 'singleline',
											enabled: true,
											errorMessage: '',
											fieldName: 'tooltip',
											instanceId: 'g2M5ceC1',
											label: '',
											locale: 'en_US',
											localizable: true,
											localizedValue: {
												en_US: '',
											},
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$tooltip$g2M5ceC1$0$$en_US',
											options: [],
											placeholder: '',
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											tip: '',
											tooltip: '',
											type: 'text',
											valid: true,
											value: '',
											visibilityExpression: 'FALSE',
											visible: false,
										},
									],
									size: 12,
								},
							],
						},
					],
					showRequiredFieldsWarning: true,
					title: 'Advanced',
				},
				{
					enabled: true,
					localizedDescription: {},
					localizedTitle: {
						en_US: 'Autocomplete',
					},
					rows: [
						{
							columns: [
								{
									fields: [
										{
											dataType: 'boolean',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											evaluable: true,
											fieldName: 'autocomplete',
											instanceId: 'zj6v70b0',
											label: 'Autocomplete',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$autocomplete$zj6v70b0$0$$en_US',
											options: [],
											predefinedValue: false,
											readOnly: false,
											repeatable: false,
											required: false,
											showAsSwitcher: true,
											showLabel: true,
											tip: '',
											type: 'checkbox',
											valid: true,
											value: false,
											visibilityExpression: 'TRUE',
											visible: true,
										},
										{
											dataType: 'string',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											evaluable: true,
											fieldName: 'dataSourceType',
											inline: false,
											instanceId: 'ew11dFA7',
											label: 'Create List',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$dataSourceType$ew11dFA7$0$$en_US',
											options: [
												{
													label: 'Manually',
													value: 'manual',
												},
												{
													label: 'From Data Provider',
													value: 'data-provider',
												},
											],
											predefinedValue: 'manual',
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: false,
											tip: '',
											type: 'radio',
											valid: true,
											value: 'manual',
											visibilityExpression: 'TRUE',
											visible: false,
										},
										{
											dataSourceType: 'data-provider',
											dataType: 'long',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											evaluable: true,
											fieldName:
												'ddmDataProviderInstanceId',
											instanceId: '4wb9VlKZ',
											label: 'Choose a Data Provider',
											locale: 'en_US',
											localizable: false,
											multiple: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$ddmDataProviderInstanceId$4wb9VlKZ$0$$en_US',
											options: [],
											predefinedValue: [],
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											strings: {
												chooseAnOption:
													'Choose an Option',
												chooseOptions: 'Choose Options',
												dynamicallyLoadedData:
													'Dynamically Loaded Data',
												emptyList:
													'No results were found.',
												search: 'Search',
											},
											tip: '',
											type: 'select',
											valid: true,
											value: [],
											visibilityExpression: 'TRUE',
											visible: false,
										},
										{
											dataSourceType: 'manual',
											dataType: 'string',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											evaluable: true,
											fieldName:
												'ddmDataProviderInstanceOutput',
											instanceId: 'g5IduymJ',
											label: 'Choose an Output Parameter',
											locale: 'en_US',
											localizable: false,
											multiple: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$ddmDataProviderInstanceOutput$g5IduymJ$0$$en_US',
											options: [],
											predefinedValue: [],
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: true,
											strings: {
												chooseAnOption:
													'Choose an Option',
												chooseOptions: 'Choose Options',
												dynamicallyLoadedData:
													'Dynamically Loaded Data',
												emptyList:
													'No results were found.',
												search: 'Search',
											},
											tip: '',
											type: 'select',
											valid: true,
											value: [],
											visibilityExpression: 'TRUE',
											visible: false,
										},
										{
											allowEmptyOptions: true,
											dataType: 'ddm-options',
											defaultLanguageId: 'en_US',
											dir: 'ltr',
											enabled: true,
											errorMessage: '',
											evaluable: true,
											fieldName: 'options',
											instanceId: 'ty1koDSW',
											label: 'Options',
											locale: 'en_US',
											localizable: false,
											name:
												'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_ddm$$options$ty1koDSW$0$$en_US',
											options: [],
											readOnly: false,
											repeatable: false,
											required: false,
											showLabel: false,
											tip: '',
											type: 'options',
											valid: true,
											value: {
												en_US: [
													{
														label: 'Option',
														value: 'Option',
													},
												],
											},
											visibilityExpression: 'TRUE',
											visible: false,
										},
									],
									size: 12,
								},
							],
						},
					],
					showRequiredFieldsWarning: false,
					title: 'Autocomplete',
				},
			],
			paginationMode: 'tabbed',
			portletNamespace:
				'_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet_',
			readOnly: false,
			rules: [
				{
					actions: [
						"call('getDataProviderInstanceOutputParameters', 'dataProviderInstanceId=ddmDataProviderInstanceId', 'ddmDataProviderInstanceOutput=outputParameterNames')",
					],
					condition:
						"not(equals(getValue('ddmDataProviderInstanceId'), ''))",
					enable: true,
				},
				{
					actions: [
						"setValue('autocomplete', FALSE)",
						"setVisible('autocomplete', FALSE)",
					],
					condition:
						"not(equals(getValue('displayStyle'), 'singleline'))",
					enable: true,
				},
				{
					actions: [
						"setRequired('ddmDataProviderInstanceId', equals(getValue('dataSourceType'), \"data-provider\"))",
						"setRequired('ddmDataProviderInstanceOutput', equals(getValue('dataSourceType'), \"data-provider\"))",
						"setValidationDataType('validation', getValue('dataType'))",
						"setValidationFieldName('validation', getValue('name'))",
						"setVisible('dataSourceType', getValue('autocomplete'))",
						"setVisible('ddmDataProviderInstanceId', equals(getValue('dataSourceType'), \"data-provider\") and getValue('autocomplete'))",
						"setVisible('ddmDataProviderInstanceOutput', equals(getValue('dataSourceType'), \"data-provider\") and getValue('autocomplete'))",
						"setVisible('options', contains(getValue('dataSourceType'), \"manual\") and getValue('autocomplete'))",
					],
					condition: 'TRUE',
					enable: true,
				},
			],
			showRequiredFieldsWarning: true,
			showSubmitButton: true,
			strings: {
				next: 'Next',
				previous: 'Previous',
			},
			submitLabel: 'Submit',
			templateNamespace: 'ddm.tabbed_form',
			viewMode: true,
		},
		system: false,
	},
];
