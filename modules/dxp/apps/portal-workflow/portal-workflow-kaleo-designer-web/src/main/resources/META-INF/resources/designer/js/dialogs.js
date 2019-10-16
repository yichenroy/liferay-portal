AUI.add(
	'liferay-kaleo-designer-dialogs',
	function(A) {
		var KaleoDesignerDialogs = {

			confirmBeforeDuplicateDialog: function(event, actionUrl, title, randomId, portletNamespace) {

				var instance = this;

				var form = A.one('#' + portletNamespace + randomId + 'form');

				if (form && !instance._forms[randomId]) {
					instance._forms[randomId] = form;
				}
				else if (!form && instance._forms[randomId]) {
					form = instance._forms[randomId];
				}

				if (form) {
					form.setAttribute('action', actionUrl);
					form.setAttribute('method', 'POST');
				}

				var duplicationDialog = instance._duplicationDialog;

				if (duplicationDialog) {
					duplicationDialog.destroy();
				}

				var dialog = Liferay.Util.Window.getWindow(
					{
						dialog: {
							bodyContent: form,
							height: 325,
							toolbars: {
								footer: [
									{
										cssClass: 'btn btn-secondary',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('cancel'),
										on: {
											click: function() {
												if (form) {
													form.reset();
												}

												dialog.hide();
											}
										}
									},
									{
										cssClass: 'btn btn-primary',
										discardDefaultButtonCssClasses: true,
										label: Liferay.Language.get('duplicate'),
										on: {
											click: function() {
												if (form) {
													submitForm(form);
												}

												dialog.hide();
											}
										}
									}
								],
								header: [
									{
										cssClass: 'close',
										discardDefaultButtonCssClasses: true,
										labelHTML: '<svg class="lexicon-icon" focusable="false"><use data-href="' + Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg#times" /><title>' + Liferay.Language.get('close') + '</title></svg>',
										on: {
											click: function(event) {
												if (form) {
													form.reset();
												}

												dialog.hide();
											}
										}
									}
								]
							},
							width: 500
						},
						title: title
					}
				);

				instance._duplicationDialog = dialog;
			},

			_duplicationDialog: null,
			_forms: {}
		};

		var openConfirmDeleteDialog = function(title, message, actionUrl) {
			var dialog = Liferay.Util.Window.getWindow(
				{
					dialog: {
						bodyContent: message,
						destroyOnHide: true,
						height: 200,
						resizable: false,
						toolbars: {
							footer: [
								{
									cssClass: 'btn btn-primary',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('delete'),
									on: {
										click: function() {
											window.location.assign(actionUrl);
										}
									}
								},
								{
									cssClass: 'btn btn-secondary',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('cancel'),
									on: {
										click: function() {
											dialog.destroy();
										}
									}
								}
							],
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML: '<svg class="lexicon-icon" focusable="false"><use data-href="' + Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg#times" /><title>' + Liferay.Language.get('close') + '</title></svg>',
									on: {
										click: function(event) {
											dialog.destroy();

											event.domEvent.stopPropagation();
										}
									}
								}
							]
						},
						width: 600
					},
					title: title
				}
			);
		};

		var showActionUndoneSuccessMessage = function(namespace) {
			var instance = this;

			var successMessage = Liferay.Language.get('action-undone');

			var alert = instance._alert;

			if (alert) {
				alert.destroy();
			}

			alert = new Liferay.Alert(
				{
					closeable: true,
					delay: {
						hide: 5000,
						show: 0
					},
					message: successMessage,
					type: 'success'
				}
			);

			if (!alert.get('rendered')) {
				alert.render('.portlet-column');
			}

			alert.show();

			instance._alert = alert;
		};

		var showDefinitionImportSuccessMessage = function(namespace) {
			var instance = this;

			var undo = Liferay.Language.get('undo');

			var undoEvent = '\'' + namespace + 'undoDefinition\'';

			var undoLink = '<a href="javascript:;" onclick=Liferay.fire(' + undoEvent + '); class="alert-link">' + undo + '</a>';

			var successMessage = Liferay.Language.get('definition-imported-sucessfully');

			successMessage += undoLink;

			var alert = instance._alert;

			if (alert) {
				alert.destroy();
			}

			alert = new Liferay.Alert(
				{
					closeable: true,
					delay: {
						hide: 10000,
						show: 0
					},
					message: successMessage,
					type: 'success'
				}
			);

			if (!alert.get('rendered')) {
				alert.render('.portlet-column');
			}

			alert.show();

			instance._alert = alert;
		};

		KaleoDesignerDialogs.openConfirmDeleteDialog = openConfirmDeleteDialog;

		KaleoDesignerDialogs.showActionUndoneSuccessMessage = showActionUndoneSuccessMessage;

		KaleoDesignerDialogs.showDefinitionImportSuccessMessage = showDefinitionImportSuccessMessage;

		Liferay.KaleoDesignerDialogs = KaleoDesignerDialogs;
	},
	'',
	{
		requires: ['liferay-util-window']
	}
);