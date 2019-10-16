AUI.add(
	'liferay-kaleo-forms-admin',
	function(A) {
		var AArray = A.Array;

		var Lang = A.Lang;

		var STEPS_MAP = {
			DETAILS: 1,
			FIELDS: 2,
			FORMS: 4,
			WORKFLOW: 3
		};

		var KaleoFormsAdmin = A.Component.create(
			{
				ATTRS: {
					currentURL: {
						value: null
					},

					form: {
						value: null
					},

					kaleoProcessId: {
						value: null
					},

					portletId: {
						value: null
					},

					saveInPortletSessionURL: {
						value: null
					},

					tabView: {
						value: null
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-kaleo-forms-admin',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance.nextBtn = instance.one('.kaleo-process-next');
						instance.prevBtn = instance.one('.kaleo-process-previous');
						instance.submitBtn = instance.one('.kaleo-process-submit');

						var formWizard = instance.formWizard = new Liferay.KaleoFormWizard(
							{
								form: instance.get('form'),
								tabView: instance.get('tabView')
							}
						);

						instance.bindUI();
						instance.syncUI();
					},

					bindUI: function() {
						var instance = this;

						var form = instance.get('form');

						form.formNode.on('submit', instance._onSubmitForm, instance);

						instance.formWizard.after('currentStepChange', instance._afterCurrentStepChange, instance);

						instance.formWizard.validator.after(['errorField', 'validField'], instance._afterValidateField, instance);

						instance.nextBtn.on('click', instance._onClickNext, instance);
						instance.prevBtn.on('click', instance._onClickPrev, instance);
					},

					syncUI: function() {
						var instance = this;

						instance.updateNavigationControls();

						instance.formWizard.validator.resetAllFields();
					},

					_afterCurrentStepChange: function(event) {
						var instance = this;

						var sessionMap = instance._getSessionMap();

						instance.saveInPortletSession(sessionMap);

						var currentStep = event.newVal;

						if (currentStep !== STEPS_MAP.DETAILS) {
							var currentName = sessionMap['name' + themeDisplay.getLanguageId()];

							if (!currentName) {
								currentName = sessionMap['name' + themeDisplay.getDefaultLanguageId()];
							}

							currentName = Liferay.Util.escapeHTML(currentName);

							instance.one('.control-menu-level-1-nav .tools-control-group span.control-menu-level-1-heading').setContent(currentName);
						}

						if (currentStep === STEPS_MAP.FORMS) {
							instance._loadFormsStep();
						}

						instance._hideSuccessMessage();

						instance.syncUI();
					},

					_afterValidateField: function(event) {
						var instance = this;

						var tabView = instance.get('tabView');

						var activeTab = tabView.getActiveTab();

						var tabViewTabs = tabView.getTabs();

						var activeTabIndex = tabViewTabs.indexOf(activeTab);

						var tabViewPanels = instance.formWizard.getTabViewPanels();

						var activePanel = tabViewPanels.item(activeTabIndex);

						if (activePanel.contains(event.validator.field)) {
							var currentStepValid = event.type.indexOf('errorField') === -1;

							instance.updateNavigationControls(currentStepValid);
						}
					},

					_getInputLocalizedValuesMap: function(inputLocalized, name) {
						var instance = this;

						var localizedValuesMap = {};

						var translatedLanguages = inputLocalized.get('translatedLanguages').values();

						localizedValuesMap['translatedLanguages' + Lang.String.capitalize(name)] = translatedLanguages.join();

						translatedLanguages.forEach(
							function(item, index, collection) {
								localizedValuesMap[name + item] = inputLocalized.getValue(item);
							}
						);

						return localizedValuesMap;
					},

					_getSessionMap: function() {
						var instance = this;

						var descriptionInputLocalized = Liferay.component(instance.ns('description'));
						var nameInputLocalized = Liferay.component(instance.ns('name'));

						var sessionMap = A.merge(
							instance._getInputLocalizedValuesMap(descriptionInputLocalized, 'description'),
							instance._getInputLocalizedValuesMap(nameInputLocalized, 'name')
						);

						var ddmStructureId = instance.one('#ddmStructureId').val();
						var ddmStructureName = instance.one('#ddmStructureName').val();
						var ddmTemplateId = instance.one('#ddmTemplateId').val();
						var kaleoTaskFormPairsData = instance.one('#kaleoTaskFormPairsData').val();
						var workflowDefinition = instance.one('#workflowDefinition').val();

						return A.merge(
							sessionMap,
							{
								ddmStructureId: ddmStructureId,
								ddmStructureName: ddmStructureName,
								ddmTemplateId: ddmTemplateId,
								kaleoTaskFormPairsData: kaleoTaskFormPairsData,
								workflowDefinition: workflowDefinition
							}
						);
					},

					_hideSuccessMessage: function() {
						var instance = this;

						var successMessageNode = instance.one('.alert-success');

						if (successMessageNode) {
							successMessageNode.hide();
						}
					},

					_isCurrentStepValid: function() {
						var instance = this;

						var formWizard = instance.formWizard;

						var currentStep = formWizard.get('currentStep');

						return formWizard.validateStep(currentStep);
					},

					_loadFormsStep: function() {
						var instance = this;

						var currentURL = instance.get('currentURL');

						var formsSearchContainer = '#' + instance.NS + 'formsSearchContainer';

						var kaleoProcessId = instance.get('kaleoProcessId');

						var resultsContainer = instance.one('#resultsContainer');

						var workflowDefinition = instance.one('#workflowDefinition').val();

						var backURL = new Liferay.PortletURL(Liferay.PortletURL.RENDER_PHASE, null, currentURL);

						backURL.setParameter('historyKey', 'forms');

						var formsURL = new Liferay.PortletURL(Liferay.PortletURL.RENDER_PHASE, null, currentURL);

						formsURL.setParameter('mvcPath', '/admin/process/task_template_search_container.jsp');
						formsURL.setParameter('backURL', backURL.toString());
						formsURL.setParameter('kaleoProcessId', kaleoProcessId);
						formsURL.setParameter('workflowDefinition', workflowDefinition);

						resultsContainer.plug(A.LoadingMask).loadingmask.show();

						resultsContainer.load(
							formsURL.toString(),
							formsSearchContainer,
							function() {
								A.each(
									A.all(formsSearchContainer + ' .lfr-icon-menu .dropdown-toggle'),
									function(item, index) {
										Liferay.Menu.register(item.get('id'));
									}
								);

								resultsContainer.unplug(A.LoadingMask);
							}
						);
					},

					_onClickNext: function(event) {
						var instance = this;

						instance.formWizard.navigate(1);
					},

					_onClickPrev: function(event) {
						var instance = this;

						instance.formWizard.navigate(-1);
					},

					_onSubmitForm: function(event) {
						var instance = this;

						event.preventDefault();

						if (instance.formWizard.validateStep(STEPS_MAP.FORMS)) {
							submitForm(event.target);
						}
					},

					saveInPortletSession: function(data, dialogId) {
						var instance = this;

						A.io.request(
							instance.get('saveInPortletSessionURL'),
							{
								data: instance.ns(data)
							}
						);
					},

					updateNavigationControls: function(currentStepValid) {
						var instance = this;

						if (currentStepValid === undefined) {
							currentStepValid = instance._isCurrentStepValid();
						}

						instance.nextBtn.attr('disabled', !currentStepValid);
						instance.nextBtn.toggleClass('disabled', !currentStepValid);

						instance.submitBtn.attr('disabled', !currentStepValid);
						instance.submitBtn.toggleClass('disabled', !currentStepValid);

						var formWizard = instance.formWizard;

						var currentStep = formWizard.get('currentStep');

						if (currentStep === STEPS_MAP.DETAILS) {
							instance.nextBtn.show();
							instance.prevBtn.hide();
							instance.submitBtn.hide();
						}
						else if (currentStep === STEPS_MAP.FORMS) {
							instance.nextBtn.hide();
							instance.prevBtn.show();
							instance.submitBtn.show();
						}
						else {
							instance.nextBtn.show();
							instance.prevBtn.show();
							instance.submitBtn.hide();
						}
					}
				}
			}
		);

		Liferay.KaleoFormsAdmin = KaleoFormsAdmin;
	},
	'',
	{
		requires: ['aui-base', 'aui-loading-mask-deprecated', 'aui-parse-content', 'aui-url', 'liferay-kaleo-forms-components', 'liferay-portlet-url', 'liferay-store', 'node-load']
	}
);