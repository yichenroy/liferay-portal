AUI().use('escape', 'aui-lang', function(A) {
	var AEscape = A.Escape;
	var ALang = A.Lang;

	var TPL_TAG_FORM =
		'<div class="form-inline {key}" >' +
		'<input class="form-control" type="text" disabled="disabled" value="{parameterKey}" /> ' +
		'<input class="form-control" type="text" disabled="disabled" value="{parameterValue}" /> ' +
		'<button class="btn btn-default remove-parameter"' +
		' data-parameterKey="{parameterKey}"' +
		' data-parameterValue="{parameterValue}"' +
		' data-parameterType="{parameterType}"' +
		' type="button">' +
		'<i class="icon-remove" aria-label="Remove"></i>' +
		'</button>' +
		'</div>';

	Liferay.Report = {
		initialize: function(param) {
			var instance = this;

			var namespace = param.namespace;

			instance._portletMessageContainer = A.one('.report-message');

			instance._displayParameters(param.parameters);

			instance._disableAddParameterButton(namespace);

			A.one('.parameters-key').on('valueChange', function() {
				instance._toggleAddParameterButton(namespace);
			});

			A.one('.parameters-value').on('valueChange', function() {
				instance._toggleAddParameterButton(namespace);
			});

			A.one('.add-parameter .btn').on('click', function() {
				instance._addParameter(namespace);
			});

			instance._createRemoveParameterEvent();

			A.one('.remove-existing-report').on('click', function() {
				A.one('.existing-report').setStyle('display', 'none');
				A.one('.template-report').setStyle('display', 'block');
				A.one('.cancel-update-template-report').setStyle(
					'display',
					'block'
				);
			});

			A.one('.cancel-update-template-report').on('click', function() {
				A.one('.existing-report').setStyle('display', 'block');
				A.one('.template-report').setStyle('display', 'none');
				A.one('.cancel-update-template-report').setStyle(
					'display',
					'none'
				);
			});

			A.one('.parameters-input-type').on('change', function(event) {
				var currentTarget = event.currentTarget;

				var parametersInputDate = A.one('.parameters-input-date');
				var parametersValue = A.one('.parameters-value');
				var parametersValueFieldSet = A.one(
					'.parameters-value-field-set'
				);

				if (currentTarget.val() == 'text') {
					parametersValue.val('');
					parametersValue.attr('disabled', '');
					parametersInputDate.setStyle('display', 'none');
					parametersValueFieldSet.setStyle('display', 'block');
				}

				if (currentTarget.val() == 'date') {
					parametersValueFieldSet.setStyle('display', 'none');
					parametersInputDate.setStyle('display', 'block');
				}

				if (currentTarget.val() == 'startDateDay') {
					parametersInputDate.setStyle('display', 'none');
					parametersValueFieldSet.setStyle('display', 'block');
					parametersValue.attr('disabled', 'disabled');
					parametersValue.val('${startDateDay}');
				}

				if (currentTarget.val() == 'endDateDay') {
					parametersInputDate.setStyle('display', 'none');
					parametersValueFieldSet.setStyle('display', 'block');
					parametersValue.attr('disabled', 'disabled');
					parametersValue.val('${endDateDay}');
				}
			});
		},

		deleteParameter: function(parameterKey, parameterValue, parameterType) {
			var instance = this;

			instance._portletMessageContainer.setStyle('display', 'none');

			if (
				confirm(
					Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this-entry'
					)
				)
			) {
				var parametersInput = A.one('.report-parameters');

				var reportParameters = JSON.parse(parametersInput.val());

				for (var i in reportParameters) {
					var reportParameter = reportParameters[i];

					if (reportParameter.key == parameterKey) {
						reportParameters.splice(i, 1);

						break;
					}
				}

				parametersInput.val(JSON.stringify(reportParameters));

				var key = ('.report-tag-' + parameterKey).replace(
					/ /g,
					'BLANK'
				);

				A.one(key).remove(true);
			}
		},

		_addParameter: function(namespace) {
			var instance = this;

			instance._portletMessageContainer.setStyle('display', 'none');

			var parameterKey = A.one('.parameters-key').val();
			var parameterType = A.one('.parameters-input-type').val();
			var parameterValue = A.one('.parameters-value').val();

			var message = '';

			if (parameterKey.length == 0) {
				A.all('.portlet-msg-error').setStyle('display', 'none');

				message = Liferay.Language.get(
					'please-enter-a-valid-report-parameter-key'
				);

				message = AEscape.html(message);

				instance._sendMessage(message);

				return;
			}

			if (parameterType != 'date' && parameterValue.length == 0) {
				A.all('.portlet-msg-error').setStyle('display', 'none');

				message = Liferay.Language.get(
					'please-enter-a-valid-report-parameter-value'
				);

				message = AEscape.html(message);

				instance._sendMessage(message);

				return;
			}

			if (
				parameterKey.indexOf(',') > 0 ||
				parameterKey.indexOf('=') > 0 ||
				parameterValue.indexOf('=') > 0
			) {
				message = Liferay.Language.get(
					'one-of-your-fields-contains-invalid-characters'
				);

				message = AEscape.html(message);

				instance._sendMessage(message);

				return;
			}

			var reportParameters = A.one('.report-parameters').val();

			if (reportParameters) {
				var reportParametersJSON = JSON.parse(reportParameters);

				for (var i in reportParametersJSON) {
					var reportParameter = reportParametersJSON[i];

					if (reportParameter.key == parameterKey) {
						message = Liferay.Language.get(
							'that-vocabulary-already-exists'
						);

						message = AEscape.html(message);

						instance._sendMessage(message);

						return;
					}
				}
			}

			if (parameterType == 'date') {
				var parameterDateDay = A.one(
					'#' + namespace + 'parameterDateDay'
				);
				var parameterDateMonth = A.one(
					'#' + namespace + 'parameterDateMonth'
				);
				var parameterDateYear = A.one(
					'#' + namespace + 'parameterDateYear'
				);

				var parameterDate = new Date();

				parameterDate.setDate(parameterDateDay.val());
				parameterDate.setMonth(parameterDateMonth.val());
				parameterDate.setYear(parameterDateYear.val());

				parameterValue = A.DataType.Date.format(parameterDate);
			}

			instance._addTag(parameterKey, parameterValue, parameterType);

			instance._addReportParameter(
				parameterKey,
				parameterValue,
				parameterType
			);
			instance._createRemoveParameterEvent();

			A.one('.parameters-key').val('');
			A.one('.parameters-value').val('');
			instance._disableAddParameterButton(namespace);
		},

		_addReportParameter: function(
			parameterKey,
			parameterValue,
			parameterType
		) {
			var reportParameters = [];

			var parametersInput = A.one('.report-parameters');

			if (parametersInput.val()) {
				reportParameters = JSON.parse(parametersInput.val());
			}

			var reportParameter = {
				key: parameterKey,
				type: parameterType,
				value: parameterValue
			};

			reportParameters.push(reportParameter);

			parametersInput.val(JSON.stringify(reportParameters));
		},

		_addTag: function(parameterKey, parameterValue, parameterType) {
			var tagsContainer = A.one('.report-tags');

			parameterKey = AEscape.html(parameterKey);
			parameterType = AEscape.html(parameterType);
			parameterValue = AEscape.html(parameterValue);

			var key = AEscape.html(
				('report-tag-' + parameterKey).replace(/ /g, 'BLANK')
			);

			var html = A.Lang.sub(TPL_TAG_FORM, {
				key: key,
				parameterKey: parameterKey,
				parameterType: parameterType,
				parameterValue: parameterValue
			});

			tagsContainer.append(html);
		},

		_createRemoveParameterEvent: function() {
			var instance = this;

			var reportTags = A.one('.report-tags');

			reportTags.delegate(
				'click',
				function(event) {
					var currentTarget = event.currentTarget;

					var parameterKey = currentTarget.getData('parameterKey');
					var parameterValue = currentTarget.getData(
						'parameterValue'
					);
					var parameterType = currentTarget.getData('parameterType');

					instance.deleteParameter(
						parameterKey,
						parameterValue,
						parameterType
					);
				},
				'.remove-parameter'
			);
		},

		_disableAddParameterButton: function() {
			A.one('.add-parameter .btn').attr('disabled', true);
		},

		_displayParameters: function(parameters) {
			var instance = this;

			instance._portletMessageContainer.setStyle('display', 'none');

			A.one('.report-parameters').val(parameters);

			if (!parameters) {
				return;
			}

			var reportParameters = JSON.parse(parameters);

			for (var i in reportParameters) {
				var reportParameter = reportParameters[i];

				if (reportParameter.key && reportParameter.value) {
					instance._addTag(
						reportParameter.key,
						reportParameter.value,
						reportParameter.type
					);
				}
			}
		},

		_enableAddParameterButton: function() {
			A.one('.add-parameter .btn').attr('disabled', false);
		},

		_sendMessage: function(message) {
			var instance = this;

			message = ALang.String.unescapeHTML(message);

			var portletMessageContainer = instance._portletMessageContainer;

			portletMessageContainer.addClass('portlet-msg-error');
			portletMessageContainer.set('innerHTML', message);
			portletMessageContainer.setStyle('display', 'block');
		},

		_toggleAddParameterButton: function(namespace) {
			var instance = this;

			var parameterKey = A.one('.parameters-key').val();
			var parameterValue = A.one('.parameters-value').val();

			if (parameterKey && parameterValue) {
				instance._enableAddParameterButton();
			} else {
				instance._disableAddParameterButton();
			}
		}
	};
});
