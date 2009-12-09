AUI().add(
	'liferay-look-and-feel',
	function(A) {
		var Lang = A.Lang;

		var PortletCSS = {
			init: function(portletId) {
				var instance = this;

				var curPortletBoundaryId = 'p_p_id_' + portletId + '_';
				var obj = A.one('#' + curPortletBoundaryId);

				if (obj) {
					instance._portletId = portletId;
					instance._curPortlet = obj.one('.portlet');
					instance._curPortletWrapperId = instance._curPortlet.attr('id');
					instance._portletBoundaryId = curPortletBoundaryId;
					instance._newPanel = A.one('#portlet-set-properties');
					instance._currentLanguage = themeDisplay.getLanguageId();

					if (!instance._curPortlet) {
						instance._curPortlet = obj;
						instance._curPortletWrapperId = curPortletBoundaryId;
					}

					if (instance._curPortlet) {
						var content = instance._newPanel;

						if (!content) {
							content = A.Node.create('<div class="loading-animation" />')
						}

						if (!instance._currentPopup) {
							instance._currentPopup = new A.Dialog(
								{
									title: Liferay.Language.get('look-and-feel'),
									width: 820,
									xy: [100, 100],
									on: {
										close: function() {
											if (Liferay.Browser.isIe() && Liferay.Browser.getMajorVersion() == 6) {
												window.location.reload(true);
											}
										}
									},
									io: {
										autoRefresh: false,
										url: themeDisplay.getPathMain() + '/portal/render_portlet',
										cfg: {
											data: {
												p_l_id: themeDisplay.getPlid(),
												p_p_id: 113,
												p_p_state: 'exclusive',
												doAsUserId: themeDisplay.getDoAsUserIdEncoded()
											},
											on: {
												success: function() {
													var host = this.get('host');
													var boundingBox = host.get('boundingBox');

													this._defSuccessHandler.apply(this, arguments);

													var properties = boundingBox.one('#portlet-set-properties');

													if (properties) {
														instance._newPanel = properties;
														instance._loadContent();
													}
												}
											}
										}
									}
								}
							).render();
						}

						instance._currentPopup.show();
						instance._currentPopup.io.refresh();
					}
				}

			},

			_backgroundStyles: function() {
				var instance = this;

				var bgData = instance._objData.bgData;

				var portlet = instance._curPortlet;

				// Background color

				var backgroundColor = instance._backgroundColor;

				var setColor = function(obj) {
					var color = obj.val();

					var cssColor = color;

					if ((color == '') || (color == '#')) {
						cssColor = 'transparent';
						color = '';
					}

					portlet.setStyle('backgroundColor', cssColor);
					bgData.backgroundColor = color;
				};

				var hexValue = backgroundColor.val().replace('#', '');

				if (!instance._backgroundColorPicker) {
					instance._backgroundColorPicker = new A.ColorPicker(
						{
							zIndex: 9999
						}
					).render();
				}

				var backgroundColorPicker = instance._backgroundColorPicker;

				var trigger = backgroundColorPicker.get('trigger');

				backgroundColor.placeAfter(trigger.item(0));

				var afterColorChange = function() {
					backgroundColor.val('#' + this.get('hex'));

					setColor(backgroundColor);
				};

				if (instance._afterBackgroundColorChangeHandler) {
					instance._afterBackgroundColorChangeHandler.detach();
				}

				instance._afterBackgroundColorChangeHandler = backgroundColorPicker.after('colorChange', afterColorChange);

				backgroundColorPicker.set('hex', hexValue);

				backgroundColor.detach('blur');

				backgroundColor.on(
					'blur',
					function(event) {
						setColor(event.currentTarget);
					}
				);
			},

			_borderStyles: function() {
				var instance = this;

				var portlet = instance._curPortlet;

				var ufaWidth = instance._ufaBorderWidth;
				var ufaStyle = instance._ufaBorderStyle;
				var ufaColor = instance._ufaBorderColor;

				var borderData = instance._objData.borderData;

				// Border width

				var wTopInt = instance._borderTopInt;
				var wTopUnit = instance._borderTopUnit;
				var wRightInt = instance._borderRightInt;
				var wRightUnit = instance._borderRightUnit;
				var wBottomInt = instance._borderBottomInt;
				var wBottomUnit = instance._borderBottomUnit;
				var wLeftInt = instance._borderLeftInt;
				var wLeftUnit = instance._borderLeftUnit;

				var changeWidth = function() {
					var styling = {};
					var borderWidth = {};

					borderWidth = instance._getCombo(wTopInt, wTopUnit);
					styling = {borderWidth: borderWidth.both};

					var ufa = ufaWidth.get('checked');

					borderData.borderWidth.top.value = borderWidth.input;
					borderData.borderWidth.top.unit = borderWidth.selectBox;
					borderData.borderWidth.sameForAll = ufa;

					if (!ufa) {
						var extStyling = {};

						extStyling.borderTopWidth = styling.borderWidth;

						var right = instance._getCombo(wRightInt, wRightUnit);
						var bottom = instance._getCombo(wBottomInt, wBottomUnit);
						var left = instance._getCombo(wLeftInt, wLeftUnit);

						extStyling.borderRightWidth = right.both;
						extStyling.borderBottomWidth = bottom.both;
						extStyling.borderLeftWidth = left.both;

						styling = extStyling;

						borderData.borderWidth.right.value = right.input;
						borderData.borderWidth.right.unit = right.selectBox;

						borderData.borderWidth.bottom.value = bottom.input;
						borderData.borderWidth.bottom.unit = bottom.selectBox;

						borderData.borderWidth.left.value = left.input;
						borderData.borderWidth.left.unit = left.selectBox;
					}

					portlet.setStyles(styling);

					changeStyle();
					changeColor();
				};

				wTopInt.detach('blur');
				wTopInt.on('blur', changeWidth);

				wTopInt.detach('keyup');
				wTopInt.on('keyup', changeWidth);

				wRightInt.detach('blur');
				wRightInt.on('blur', changeWidth);

				wRightInt.detach('keyup');
				wRightInt.on('keyup', changeWidth);

				wBottomInt.detach('blur');
				wBottomInt.on('blur', changeWidth);

				wBottomInt.detach('keyup');
				wBottomInt.on('keyup', changeWidth);

				wLeftInt.detach('blur');
				wLeftInt.on('blur', changeWidth);

				wLeftInt.detach('keyup');
				wLeftInt.on('keyup', changeWidth);

				wTopUnit.detach('change');
				wTopUnit.on('change', changeWidth);

				wRightUnit.detach('change');
				wRightUnit.on('change', changeWidth);

				wBottomUnit.detach('change');
				wBottomUnit.on('change', changeWidth);

				wLeftUnit.detach('change');
				wLeftUnit.on('change', changeWidth);

				ufaWidth.detach('click');
				ufaWidth.on('click', changeWidth);

				// Border style

				var sTopStyle = instance._borderTopStyle;
				var sRightStyle = instance._borderRightStyle;
				var sBottomStyle = instance._borderBottomStyle;
				var sLeftStyle = instance._borderLeftStyle;

				var changeStyle = function() {
					var styling = {};
					var borderStyle = {};

					borderStyle = sTopStyle.val();
					styling = {borderStyle: borderStyle};

					var ufa = ufaStyle.get('checked');

					borderData.borderStyle.top = borderStyle;
					borderData.borderStyle.sameForAll = ufa;

					if (!ufa) {
						var extStyling = {};

						extStyling.borderTopStyle = styling.borderStyle;

						var right = sRightStyle.val();
						var bottom = sBottomStyle.val();
						var left = sLeftStyle.val();

						extStyling.borderRightStyle = right;
						extStyling.borderBottomStyle = bottom;
						extStyling.borderLeftStyle = left;

						styling = extStyling;

						borderData.borderStyle.right = right;

						borderData.borderStyle.bottom = bottom;

						borderData.borderStyle.left = left;
					}

					portlet.setStyles(styling);
				};

				sTopStyle.detach('change');
				sTopStyle.on('change', changeStyle);

				sRightStyle.detach('change');
				sRightStyle.on('change', changeStyle);

				sBottomStyle.detach('change');
				sBottomStyle.on('change', changeStyle);

				sLeftStyle.detach('change');
				sLeftStyle.on('change', changeStyle);

				ufaStyle.detach('click');
				ufaStyle.on('click', changeStyle);

				// Border color

				var cTopColor = instance._borderTopColor;
				var cRightColor = instance._borderRightColor;
				var cBottomColor = instance._borderBottomColor;
				var cLeftColor = instance._borderLeftColor;

				var changeColor = function() {
					var styling = {};
					var borderColor = {};

					borderColor = cTopColor.val();
					styling = {borderColor: borderColor};

					var ufa = ufaColor.get('checked');

					borderData.borderColor.top = borderColor;
					borderData.borderColor.sameForAll = ufa;

					if (!ufa) {
						var extStyling = {};

						extStyling.borderTopColor = styling.borderColor;

						var right = cRightColor.val();
						var bottom = cBottomColor.val();
						var left = cLeftColor.val();

						extStyling.borderRightColor = right;
						extStyling.borderBottomColor = bottom;
						extStyling.borderLeftColor = left;

						styling = extStyling;

						borderData.borderColor.right = right;

						borderData.borderColor.bottom = bottom;

						borderData.borderColor.left = left;
					}

					portlet.setStyles(styling);
				};

				A.each(
					[cTopColor, cRightColor, cBottomColor, cLeftColor],
					function(item, index, collection) {
						var hexValue = item.val().replace('#', '');

						var borderLocation = '_borderColorPicker' + index;

						if (!instance[borderLocation]) {
							instance[borderLocation] = new A.ColorPicker(
								{
									zIndex: 9999
								}
							).render();
						}

						var borderColorPicker = instance[borderLocation];

						var trigger = borderColorPicker.get('trigger');

						item.placeAfter(trigger.item(0));

						var afterColorChange = function() {
							item.val('#' + this.get('hex'));

							changeColor();
						};

						var borderColorChangeHandler = '_afterBorderColorChangeHandler' + index;

						if (instance[borderColorChangeHandler]) {
							instance[borderColorChangeHandler].detach();
						}

						instance[borderColorChangeHandler] = borderColorPicker.after('colorChange', afterColorChange);

						borderColorPicker.set('hex', hexValue);
					}
				);

				cTopColor.detach('blur');
				cTopColor.on('blur', changeColor);

				cRightColor.detach('blur');
				cRightColor.on('blur', changeColor);

				cBottomColor.detach('blur');
				cBottomColor.on('blur', changeColor);

				cLeftColor.detach('blur');
				cLeftColor.on('blur', changeColor);

				cTopColor.detach('keyup');
				cTopColor.on('keyup', changeColor);

				cRightColor.detach('keyup');
				cRightColor.on('keyup', changeColor);

				cBottomColor.detach('keyup');
				cBottomColor.on('keyup', changeColor);

				cLeftColor.detach('keyup');
				cLeftColor.on('keyup', changeColor);

				ufaColor.detach('click');
				ufaColor.on('click', changeColor);
			},

			_cssStyles: function() {
				var instance = this;

				var portlet = instance._curPortlet;

				var customCSS = A.one('#lfr-custom-css');
				var customCSSContainer = customCSS.ancestor('.aui-ctrl-holder');
				var customPortletNoteHTML = '<p class="portlet-msg-info form-hint"></p>';
				var customPortletNote = A.one('#lfr-portlet-info');
				var refreshText = '';

				var portletId = instance._curPortletWrapperId;
				var portletClasses = portlet.get('className');

				portletClasses = Lang.trim(portletClasses).replace(/(\s)/g, '$1.');

				var portletInfoText =
					Liferay.Language.get('your-current-portlet-information-is-as-follows') + ':<br />' +
						Liferay.Language.get('portlet-id') + ': <strong>#' + portletId + '</strong><br />' +
							Liferay.Language.get('portlet-classes') + ': <strong>.' + portletClasses + '</strong>';

				var customNote = A.one('#lfr-refresh-styles');

				if (!customNote) {
					customNote = A.Node.create(customPortletNoteHTML);

					customNote.setAttrs(
						{
							'className': '',
							id: 'lfr-refresh-styles'
						}
					);
				}

				if (!customPortletNote) {
					customPortletNote = A.Node.create(customPortletNoteHTML);
					customCSSContainer.placeBefore(customPortletNote);

					customPortletNote.attr('id', 'lfr-portlet-info');
				}

				customPortletNote.html(portletInfoText);

				Liferay.Util.enableTextareaTabs(customCSS.getDOM());

				if (!Liferay.Browser.isSafari()) {
					refreshText = Liferay.Language.get('update-the-styles-on-this-page');

					var refreshLink = A.Node.create('<a href="javascript:;">' + refreshText + '</a>');

					var customStyleBlock = A.one('#lfr-custom-css-block-' + portletId);

					if (!customStyleBlock) {

						// Do not modify. This is a workaround for an IE bug.

						var styleEl = document.createElement('style');

						styleEl.id = 'lfr-custom-css-block-' + portletId;
						styleEl.className = 'lfr-custom-css-block';
						styleEl.setAttribute('type', 'text/css');

						document.getElementsByTagName('head')[0].appendChild(styleEl);
					}
					else {
						styleEl = customStyleBlock.getDOM();
					}

					var refreshStyles = function() {
						var customStyles = customCSS.val();

						customStyles = customStyles.replace(/<script[^>]*>([\u0001-\uFFFF]*?)<\/script>/gim, '');
						customStyles = customStyles.replace(/<\/?[^>]+>/gi, '');

						if (styleEl.styleSheet) { // for IE only
							if (customStyles == '') {

								// Do not modify. This is a workaround for an IE bug.

								customStyles = '<!---->';
							}

							styleEl.styleSheet.cssText = customStyles;
						}
						else {
							A.one(styleEl).html(customStyles);
						}
					};

					refreshLink.detach('click');
					refreshLink.on('click', refreshStyles);

					customNote.empty().append(refreshLink);
				}
				else {
					refreshText = Liferay.Language.get('please-press-the-save-button-to-view-your-changes');

					customNote.empty().text(refreshText);
				}

				var insertContainer = A.one('#lfr-add-rule-container');
				var addIdLink = A.one('#lfr-add-id');
				var addClassLink = A.one('#lfr-add-class');
				var updateOnType = A.one('#lfr-update-on-type');

				if (!insertContainer) {
					insertContainer = A.Node.create('<div id="lfr-add-rule-container"></div>');
					addIdLink = A.Node.create('<a href="javascript:;" id="lfr-add-id">' + Liferay.Language.get('add-a-css-rule-for-just-this-portlet') + '</a>');
					addClassLink = A.Node.create('<a href="javascript:;" id="lfr-add-class">' + Liferay.Language.get('add-a-css-rule-for-all-portlets-like-this-one') + '</a>');

					var updateOnTypeHolder = A.Node.create('<div class="aui-ctrl-holder"></div>');
					var updateOnTypeLabel = A.Node.create('<label>' + Liferay.Language.get('update-my-styles-as-i-type') + ' </label>');

					updateOnType = A.Node.create('<input id="lfr-update-on-type" type="checkbox" />');

					updateOnTypeLabel.appendChild(updateOnType);
					updateOnTypeHolder.appendChild(updateOnTypeLabel);

					customCSSContainer.placeAfter(insertContainer);

					insertContainer.appendChild(addIdLink);

					insertContainer.append('<br />');

					insertContainer.appendChild(addClassLink);
					insertContainer.appendChild(updateOnTypeHolder);

					insertContainer.after(customNote);
				}

				updateOnType.on(
					'click',
					function(event) {
						if (event.currentTarget.get('checked')) {
							customNote.hide();
							customCSS.on('keyup', refreshStyles);
						}
						else {
							customNote.show();
							customCSS.detach('keyup', refreshStyles);
						}
					}
				);

				addIdLink.detach('click');

				addIdLink.on(
					'click',
					function() {
						customCSS.getDOM().value += '\n#' + portletId + '{\n\t\n}\n';
					}
				);

				addClassLink.detach('click');

				addClassLink.on(
					'click',
					function() {
						customCSS.getDOM().value += '\n.' + portletClasses.replace(/\s/g, '') + '{\n\t\n}\n';
					}
				);
			},

			_getCombo: function(input, selectBox) {
				var instance = this;

				var inputVal = input.val();
				var selectVal = selectBox.val();

				inputVal = instance._getSafeInteger(inputVal);

				return {input: inputVal, selectBox: selectVal, both: inputVal + selectVal};
			},

			_getSafeInteger: function(input) {
				var instance = this;

				var output = parseInt(input);

				if (output == '' || isNaN(output)) {
					output = 0;
				}

				return output;
			},

			_languageClasses: function(key, value, removeClass) {
				var instance = this;

				var option = instance._portletLanguage.one('option[value=' + key + ']');

				if (option) {
					if (removeClass) {
						option.removeClass('focused');
					}
					else {
						option.addClass('focused');
					}
				}
			},

			_loadContent: function(instantiated) {
				var instance = this;

				var newPanel = instance._newPanel;

				if (!instantiated) {
					newPanel.addClass('instantiated');
					instance._portletBoundaryIdVar = A.one('#portlet-boundary-id');

					// Portlet config

					instance._customTitleInput = A.one('#custom-title');

					var portletTitle = instance._curPortlet.one('.portlet-title');

					instance._defaultPortletTitle = (portletTitle && portletTitle.text()) || '';
					instance._customTitleCheckbox = A.one('#use-custom-title-checkbox');
					instance._showBorders = A.one('#show-borders');
					instance._borderNote = A.one('#border-note');
					instance._portletLanguage = A.one('#lfr-portlet-language');
					instance._portletLinksTarget = A.one('#lfr-point-links');

					// Text

					instance._fontFamily = A.one('#lfr-font-family');
					instance._fontWeight = A.one('#lfr-font-bold');
					instance._fontStyle = A.one('#lfr-font-italic');
					instance._fontSize = A.one('#lfr-font-size');
					instance._fontColor = A.one('#lfr-font-color');
					instance._textAlign = A.one('#lfr-font-align');
					instance._textDecoration = A.one('#lfr-font-decoration');
					instance._wordSpacing = A.one('#lfr-font-space');
					instance._leading = A.one('#lfr-font-leading');
					instance._tracking = A.one('#lfr-font-tracking');

					// Background

					instance._backgroundColor = A.one('#lfr-bg-color');

					// Border

					instance._ufaBorderWidth = A.one('#lfr-use-for-all-width');
					instance._ufaBorderStyle = A.one('#lfr-use-for-all-style');
					instance._ufaBorderColor = A.one('#lfr-use-for-all-color');

					instance._borderTopInt = A.one('#lfr-border-width-top');
					instance._borderTopUnit = A.one('#lfr-border-width-top-unit');
					instance._borderRightInt = A.one('#lfr-border-width-right');
					instance._borderRightUnit = A.one('#lfr-border-width-right-unit');
					instance._borderBottomInt = A.one('#lfr-border-width-bottom');
					instance._borderBottomUnit = A.one('#lfr-border-width-bottom-unit');
					instance._borderLeftInt = A.one('#lfr-border-width-left');
					instance._borderLeftUnit = A.one('#lfr-border-width-left-unit');

					instance._borderTopStyle = A.one('#lfr-border-style-top');
					instance._borderRightStyle = A.one('#lfr-border-style-right');
					instance._borderBottomStyle = A.one('#lfr-border-style-bottom');
					instance._borderLeftStyle = A.one('#lfr-border-style-left');

					instance._borderTopColor = A.one('#lfr-border-color-top');
					instance._borderRightColor = A.one('#lfr-border-color-right');
					instance._borderBottomColor = A.one('#lfr-border-color-bottom');
					instance._borderLeftColor = A.one('#lfr-border-color-left');

					// Spacing

					instance._ufaPadding = A.one('#lfr-use-for-all-padding');
					instance._ufaMargin = A.one('#lfr-use-for-all-margin');

					instance._paddingTopInt = A.one('#lfr-padding-top');
					instance._paddingTopUnit = A.one('#lfr-padding-top-unit');
					instance._paddingRightInt = A.one('#lfr-padding-right');
					instance._paddingRightUnit = A.one('#lfr-padding-right-unit');
					instance._paddingBottomInt = A.one('#lfr-padding-bottom');
					instance._paddingBottomUnit = A.one('#lfr-padding-bottom-unit');
					instance._paddingLeftInt = A.one('#lfr-padding-left');
					instance._paddingLeftUnit = A.one('#lfr-padding-left-unit');

					instance._marginTopInt = A.one('#lfr-margin-top');
					instance._marginTopUnit = A.one('#lfr-margin-top-unit');
					instance._marginRightInt = A.one('#lfr-margin-right');
					instance._marginRightUnit = A.one('#lfr-margin-right-unit');
					instance._marginBottomInt = A.one('#lfr-margin-bottom');
					instance._marginBottomUnit = A.one('#lfr-margin-bottom-unit');
					instance._marginLeftInt = A.one('#lfr-margin-left');
					instance._marginLeftUnit = A.one('#lfr-margin-left-unit');

					// Advanced CSS

					instance._customCSS = A.one('#lfr-custom-css');

					instance._saveButton = A.one('#lfr-lookfeel-save');
					instance._resetButton = A.one('#lfr-lookfeel-reset');

					// WAP styling

					instance._wapTitleInput = A.one('#lfr-wap-title');
					instance._wapInitialWindowStateSelect = A.one('#lfr-wap-initial-window-state');

				}

				instance._tabs = new A.TabView(
					{
						listNode: newPanel.one('.aui-tabview-list'),
						contentNode: newPanel.one('.aui-tabview-content')
					}
				).render(newPanel.one('form'));

				newPanel.show();

				newPanel.all('.lfr-colorpicker-img').remove();

				instance._portletMsgResponse = A.one('#lfr-portlet-css-response');

				if (instance._portletMsgResponse) {
					instance._portletMsgResponse.hide();
				}

				var defaultData = {
					advancedData: {
						customCSS: ''
					},

					bgData: {
						backgroundColor: '',
						backgroundImage: '',
						useBgImage: false,
						backgroundRepeat: '',
						backgroundPosition: {
							left: {
								value: '',
								unit: 'px'
							},
							top: {
								value: '',
								unit: 'px'
							}
						}
					},

					borderData: {
						borderWidth: {
							bottom: {
								value: '',
								unit: 'px'
							},
							left: {
								value: '',
								unit: 'px'
							},
							right: {
								value: '',
								unit: 'px'
							},
							top: {
								value: '',
								unit: 'px'
							},
							sameForAll: true
						},

						borderStyle: {
							bottom: '',
							left: '',
							right: '',
							top: '',
							sameForAll: true
						},

						borderColor: {
							bottom: '',
							left: '',
							right: '',
							top: '',
							sameForAll: true
						}
					},

					portletData: {
						language: 'en_US',
						portletLinksTarget: '',
						showBorders: themeDisplay.getPortletSetupShowBordersDefault(),
						title: '',
						titles: {},
						useCustomTitle: false
					},

					spacingData: {
						margin: {
							bottom: {
								value: '',
								unit: 'px'
							},
							left: {
								value: '',
								unit: 'px'
							},
							right: {
								value: '',
								unit: 'px'
							},
							top: {
								value: '',
								unit: 'px'
							},
							sameForAll: true
						},
						padding: {
							bottom: {
								value: '',
								unit: 'px'
							},
							left: {
								value: '',
								unit: 'px'
							},
							right: {
								value: '',
								unit: 'px'
							},
							top: {
								value: '',
								unit: 'px'
							},
							sameForAll: true
						}

					},

					textData: {
						textAlign: '',
						color: '',
						fontFamily: '',
						fontSize: '',
						fontStyle: '',
						fontWeight: '',
						letterSpacing: '',
						lineHeight: '',
						textDecoration: '',
						wordSpacing: ''
					},

					wapData: {
						title: '',
						initialWindowState: 'NORMAL'
					}
				};

				var onLookAndFeelComplete = function() {
					instance._portletBoundaryIdVar.val(instance._curPortletWrapperId);

					instance._setDefaults();

					instance._portletConfig();
					instance._textStyles();
					instance._backgroundStyles();
					instance._borderStyles();
					instance._spacingStyles();
					instance._cssStyles();

					var useForAll = newPanel.all('.lfr-use-for-all');

					var handleForms = function(item, index, collection) {
						var checkBox = item;

						var otherHolders = checkBox.ancestor('fieldset').all('.aui-ctrl-holder');

						var checked = item.get('checked');

						otherHolders.each(
							function(holderItem, holderIndex, holderCollection) {
								if (holderIndex > 1) {
									var fields = holderItem.all('input, select');
									var colorPickerImages = holderItem.all('.lfr-colorpicker-img');

									var action = 'show';
									var disabled = false;
									var opacity = 1;

									if (checked) {
										action = 'hide';
										disabled = true;
										opacity = 0.3;
									}

									holderItem.setStyle('opacity', opacity);
									fields.set('disabled', disabled);
									colorPickerImages[action]();
								}
							}
						);
					};

					useForAll.detach('click');

					useForAll.on(
						'click',
						function(event) {
							handleForms(event.currentTarget);
						}
					);

					useForAll.each(handleForms);

					var saveHandler = function(xHR, type) {
						var ajaxResponseMsg = instance._portletMsgResponse;
						var ajaxResponseHTML = '<div id="lfr-portlet-css-response"></div>';
						var message = '';
						var messageClass = '';

						if (type == 'success') {
							message = Liferay.Language.get('your-request-processed-successfully');
							messageClass = 'portlet-msg-success';
						}
						else {
							message = Liferay.Language.get('your-settings-could-not-be-saved');
							messageClass = 'portlet-msg-error';
						}

						if (!ajaxResponseMsg) {
							ajaxResponse = A.Node.create(ajaxResponseHTML);
							newPanel.one('form').prepend(ajaxResponse);

							instance._portletMsgResponse = ajaxResponse;
						}

						ajaxResponse.hide();
						ajaxResponse.attr('class', messageClass);
						ajaxResponse.empty();
						ajaxResponse.html(message);
						ajaxResponse.fadeIn('normal');
					};

					instance._saveButton.detach('click');

					instance._saveButton.on(
						'click',
						function() {
							instance._objData.advancedData.customCSS = instance._customCSS.val();

							instance._objData.wapData.title = instance._wapTitleInput.val();
							instance._objData.wapData.initialWindowState = instance._wapInitialWindowStateSelect.val();

							A.io(
								themeDisplay.getPathMain() + '/portlet_configuration/update_look_and_feel',
								{
									data: A.toQueryString(
										{
											p_l_id: themeDisplay.getPlid(),
											doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
											portletId: instance._portletId,
											css: A.JSON.stringify(instance._objData)
										}
									),
									method: 'POST',
									on: {
										complete: saveHandler
									}
								}
							);
						}
					);

					instance._resetButton.detach('click');

					instance._resetButton.on(
						'click',
						function() {
							instance._curPortlet.attr('style', '');

							var customStyle = A.one('#lfr-custom-css-block-' + instance._curPortletWrapperId)

							if (customStyle) {
								customStyle.remove();
							}

							instance._objData = defaultData;
							instance._setDefaults();
						}
					);
				};

				instance._objData = defaultData;

				A.io(
					themeDisplay.getPathMain() + '/portlet_configuration/get_look_and_feel',
					{
						data: A.toQueryString(
							{
								p_l_id: themeDisplay.getPlid(),
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								portletId: instance._portletId
							}
						),
						method: 'POST',
						on: {
							complete: function(i, o) {
								try {
									var objectData = A.JSON.parse(o.responseText);
								}
								catch(e) {}

								if (objectData && objectData.responseText.length) {
									instance._objData = objectData;
								}

								onLookAndFeelComplete();
							}
						}
					}
				);
			},

			_portletConfig: function() {
				var instance = this;

				var portletData = instance._objData.portletData;
				var customTitleInput = instance._customTitleInput;
				var customTitleCheckbox = instance._customTitleCheckbox;
				var showBorders = instance._showBorders;
				var language = instance._portletLanguage;
				var borderNote = instance._borderNote;
				var portletLinksTarget = instance._portletLinksTarget;

				// Use custom title

				customTitleCheckbox.detach('click');

				customTitleCheckbox.on(
					'click',
					function(event) {
						var title;
						var portletTitle = instance._curPortlet.one('.portlet-title');

						var checked = event.currentTarget.get('checked');

						portletData.useCustomTitle = checked;

						if (checked) {
							customTitleInput.set('disabled', false);
							language.set('disabled', false);
							title = Lang.trim(customTitleInput.val());

							if (title == '') {
								title = (portletTitle && portletTitle.text()) || '';
								title = Lang.trim(title);
								customTitleInput.val(title);
							}

							portletData.title = title;
							instance._portletTitles(false, title);
						}
						else {
							customTitleInput.attr('disabled', true);
							language.attr('disabled', true);
							title = instance._defaultPortletTitle;
						}

						if (portletTitle) {
							portletTitle.text(title);
						}
					}
				);

				customTitleInput.detach('keyup');

				customTitleInput.on(
					'keyup',
					function(event) {
						if (!portletData.useCustomTitle || instance._portletLanguage.val() != instance._currentLanguage) {
							return;
						}

						var portletTitle = instance._curPortlet.one('.portlet-title');

						if (portletTitle) {
							var cruft = portletTitle.html().match(/<\/?[^>]+>|\n|\r|\t/gim);

							if (cruft) {
								cruft = cruft.join('');
							}
							else {
								cruft = '';
							}

							var value = event.currentTarget.val();

							portletTitle.html(cruft + value);

							portletData.title = value;
							instance._portletTitles(false, value);
						}
					}
				);

				// Show borders

				showBorders.detach('click');

				showBorders.on(
					'click',
					function(event) {
						borderNote.toggle();
						portletData.showBorders = event.currentTarget.get('checked');
					}
				);

				language.on(
					'change',
					function(event) {
						portletData.language = event.currentTarget.val();

						var title = instance._portletTitles(portletData.language);

						if (portletData.useCustomTitle) {
							customTitleInput.val(title);
						}
					}
				);

				// Point target links to

				portletLinksTarget.on(
					'change',
					function(event) {
						portletData.portletLinksTarget = instance._getSafeInteger(event.currentTarget.val());
					}
				);
			},

			_portletTitles: function(key, value) {
				var instance = this;

				var portletLanguage = instance._portletLanguage;

				if (!instance._objData.portletData.titles) {
					instance._objData.portletData.titles = {};
				}

				var portletTitles = instance._objData.portletData.titles;

				if (!key) {
					key = instance._portletLanguage.val();
				}

				if (value == null) {
					var portletTitle = portletTitles[key];

					if (portletTitle) {
						return portletTitle;
					}

					return '';
				}
				else {
					portletTitles[key] = value;

					if (value == '') {
						instance._languageClasses(key, null, true);
					}
					else {
						instance._languageClasses(key);
					}
				}
			},

			_setCheckbox: function(obj, value) {
				var instance = this;

				if (obj) {
					obj.set('checked', value);
				}
			},

			_setDefaults: function() {
				var instance = this;

				var objData = instance._objData;

				var portletData = objData.portletData;
				var textData = objData.textData;
				var bgData = objData.bgData;
				var borderData = objData.borderData;
				var spacingData = objData.spacingData;
				var wapData = objData.wapData;

				if (wapData == null) {
					wapData = {
						title: '',
						initialWindowState: 'NORMAL'
					};

					objData.wapData = wapData;
				}

				var portletTitles = portletData.titles;
				var portletTitle = instance._portletTitles(portletData.language);

				var fontStyle = false;
				var fontWeight = false;

				if (textData.fontStyle != 'normal') {
					fontStyle = true;
				}

				if (textData.fontWeight != 'normal') {
					fontWeight = true;
				}

				// Portlet config

				instance._setInput(instance._customTitleInput, portletTitle);
				instance._setCheckbox(instance._customTitleCheckbox, portletData.useCustomTitle);
				instance._setCheckbox(instance._showBorders, portletData.showBorders);
				instance._setSelect(instance._portletLanguage, instance._currentLanguage);
				instance._setSelect(instance._portletLinksTarget, portletData.portletLinksTarget);

				if (!portletData.useCustomTitle) {
					instance._customTitleInput.set('disabled', true);
					instance._portletLanguage.set('disabled', true);
				}

				if (portletData.titles) {
					A.each(
						portletData.titles,
						function(item, index, collection) {
							instance._languageClasses(item);
						}
					);
				}

				// Text

				instance._setSelect(instance._fontFamily, textData.fontFamily);
				instance._setCheckbox(instance._fontWeight, fontWeight);
				instance._setCheckbox(instance._fontStyle, fontStyle);
				instance._setSelect(instance._fontSize, textData.fontSize);
				instance._setInput(instance._fontColor, textData.color);
				instance._setSelect(instance._textAlign, textData.textAlign);
				instance._setSelect(instance._textDecoration, textData.textDecoration);
				instance._setSelect(instance._wordSpacing, textData.wordSpacing);
				instance._setSelect(instance._leading, textData.lineHeight);
				instance._setSelect(instance._tracking, textData.letterSpacing);

				// Background

				instance._setInput(instance._backgroundColor, bgData.backgroundColor);

				// Border

				instance._setCheckbox(instance._ufaBorderWidth, borderData.borderWidth.sameForAll);
				instance._setCheckbox(instance._ufaBorderStyle, borderData.borderStyle.sameForAll);
				instance._setCheckbox(instance._ufaBorderColor, borderData.borderColor.sameForAll);

				instance._setInput(instance._borderTopInt, borderData.borderWidth.top.value);
				instance._setSelect(instance._borderTopUnit, borderData.borderWidth.top.unit);
				instance._setInput(instance._borderRightInt, borderData.borderWidth.right.value);
				instance._setSelect(instance._borderRightUnit, borderData.borderWidth.right.unit);
				instance._setInput(instance._borderBottomInt, borderData.borderWidth.bottom.value);
				instance._setSelect(instance._borderBottomUnit, borderData.borderWidth.bottom.unit);
				instance._setInput(instance._borderLeftInt, borderData.borderWidth.left.value);
				instance._setSelect(instance._borderLeftUnit, borderData.borderWidth.left.unit);

				instance._setSelect(instance._borderTopStyle, borderData.borderStyle.top);
				instance._setSelect(instance._borderRightStyle, borderData.borderStyle.right);
				instance._setSelect(instance._borderBottomStyle, borderData.borderStyle.bottom);
				instance._setSelect(instance._borderLeftStyle, borderData.borderStyle.left);

				instance._setInput(instance._borderTopColor, borderData.borderColor.top);
				instance._setInput(instance._borderRightColor, borderData.borderColor.right);
				instance._setInput(instance._borderBottomColor, borderData.borderColor.bottom);
				instance._setInput(instance._borderLeftColor, borderData.borderColor.left);

				// Spacing

				instance._setCheckbox(instance._ufaPadding, spacingData.padding.sameForAll);
				instance._setCheckbox(instance._ufaMargin, spacingData.margin.sameForAll);

				instance._setInput(instance._paddingTopInt, spacingData.padding.top.value);
				instance._setSelect(instance._paddingTopUnit, spacingData.padding.top.unit);
				instance._setInput(instance._paddingRightInt, spacingData.padding.right.value);
				instance._setSelect(instance._paddingRightUnit, spacingData.padding.right.unit);
				instance._setInput(instance._paddingBottomInt, spacingData.padding.bottom.value);
				instance._setSelect(instance._paddingBottomUnit, spacingData.padding.bottom.unit);
				instance._setInput(instance._paddingLeftInt, spacingData.padding.left.value);
				instance._setSelect(instance._paddingLeftUnit, spacingData.padding.left.unit);

				instance._setInput(instance._marginTopInt, spacingData.margin.top.value);
				instance._setSelect(instance._marginTopUnit, spacingData.margin.top.unit);
				instance._setInput(instance._marginRightInt, spacingData.margin.right.value);
				instance._setSelect(instance._marginRightUnit, spacingData.margin.right.unit);
				instance._setInput(instance._marginBottomInt, spacingData.margin.bottom.value);
				instance._setSelect(instance._marginBottomUnit, spacingData.margin.bottom.unit);
				instance._setInput(instance._marginLeftInt, spacingData.margin.left.value);
				instance._setSelect(instance._marginLeftUnit, spacingData.margin.left.unit);

				// Advanced CSS

				var customStyleBlock = A.one('#lfr-custom-css-block-' + instance._curPortletWrapperId);

				var customStyles = customStyleBlock && customStyleBlock.html();

				if (customStyles == '' || customStyles == null) {
					customStyles = objData.advancedData.customCSS;
				}

				instance._setTextarea(instance._customCSS, customStyles);

				// WAP styling

				instance._setInput(instance._wapTitleInput, wapData.title);
				instance._setSelect(instance._wapInitialWindowStateSelect, wapData.initialWindowState);
			},

			_setInput: function(obj, value) {
				var instance = this;

				if (obj) {
					obj.val(value);
				}
			},

			_setSelect: function(obj, value) {
				var instance = this;

				if (obj) {
					var option = obj.one('option[value=' + value + ']');

					if (option) {
						option.attr('selected', 'selected');
					}
				}
			},

			_setTextarea: function(obj, value) {
				var instance = this;

				instance._setInput(obj, value);
			},

			_spacingStyles: function() {
				var instance = this;

				var portlet = instance._curPortlet;

				var ufaPadding = instance._ufaPadding;
				var ufaMargin = instance._ufaMargin;

				var spacingData = instance._objData.spacingData;

				// Padding

				var pTop = instance._paddingTopInt;
				var pTopUnit = instance._paddingTopUnit;
				var pRight = instance._paddingRightInt;
				var pRightUnit = instance._paddingRightUnit;
				var pBottom = instance._paddingBottomInt;
				var pBottomUnit = instance._paddingBottomUnit;
				var pLeft = instance._paddingLeftInt;
				var pLeftUnit = instance._paddingLeftUnit;

				var changePadding = function() {
					var styling = {};

					var padding = instance._getCombo(pTop, pTopUnit);

					styling = {padding: padding.both};

					var ufa = ufaPadding.get('checked');

					spacingData.padding.top.value = padding.input;
					spacingData.padding.top.unit = padding.selectBox;

					spacingData.padding.sameForAll = ufa;

					if (!ufa) {
						var extStyling = {};

						extStyling.paddingTop = styling.padding;

						var right = instance._getCombo(pRight, pRightUnit);
						var bottom = instance._getCombo(pBottom, pBottomUnit);
						var left = instance._getCombo(pLeft, pLeftUnit);

						extStyling.paddingRight = right.both;
						extStyling.paddingBottom = bottom.both;
						extStyling.paddingLeft = left.both;

						styling = extStyling;

						spacingData.padding.right.value = right.input;
						spacingData.padding.right.unit = right.selectBox;

						spacingData.padding.bottom.value = bottom.input;
						spacingData.padding.bottom.unit = bottom.selectBox;

						spacingData.padding.left.value = left.input;
						spacingData.padding.left.unit = left.selectBox;
					}

					portlet.setStyles(styling);
				};

				pTop.detach('blur');
				pTop.on('blur', changePadding);

				pRight.detach('blur');
				pRight.on('blur', changePadding);

				pBottom.detach('blur');
				pBottom.on('blur', changePadding);

				pLeft.detach('blur');
				pLeft.on('blur', changePadding);

				pTop.detach('keyup');
				pTop.on('keyup', changePadding);

				pRight.detach('keyup');
				pRight.on('keyup', changePadding);

				pBottom.detach('keyup');
				pBottom.on('keyup', changePadding);

				pLeft.detach('keyup');
				pLeft.on('keyup', changePadding);

				pTopUnit.detach('change');
				pTopUnit.on('change', changePadding);

				pRightUnit.detach('change');
				pRightUnit.on('change', changePadding);

				pBottomUnit.detach('change');
				pBottomUnit.on('change', changePadding);

				pLeftUnit.detach('change');
				pLeftUnit.on('change', changePadding);

				ufaPadding.detach('click');
				ufaPadding.on('click', changePadding);

				// Margin

				var mTop = instance._marginTopInt;
				var mTopUnit = instance._marginTopUnit;
				var mRight = instance._marginRightInt;
				var mRightUnit = instance._marginRightUnit;
				var mBottom = instance._marginBottomInt;
				var mBottomUnit = instance._marginBottomUnit;
				var mLeft = instance._marginLeftInt;
				var mLeftUnit = instance._marginLeftUnit;

				var changeMargin = function() {
					var styling = {};

					var margin = instance._getCombo(mTop, mTopUnit);

					styling = {margin: margin.both};

					var ufa = ufaMargin.get('checked');

					spacingData.margin.top.value = margin.input;
					spacingData.margin.top.unit = margin.selectBox;

					spacingData.margin.sameForAll = ufa;

					if (!ufa) {
						var extStyling = {};

						extStyling.marginTop = styling.margin;

						var right = instance._getCombo(mRight, mRightUnit);
						var bottom = instance._getCombo(mBottom, mBottomUnit);
						var left = instance._getCombo(mLeft, mLeftUnit);

						extStyling.marginRight = right.both;
						extStyling.marginBottom = bottom.both;
						extStyling.marginLeft = left.both;

						styling = extStyling;

						spacingData.margin.right.value = right.input;
						spacingData.margin.right.unit = right.selectBox;

						spacingData.margin.bottom.value = bottom.input;
						spacingData.margin.bottom.unit = bottom.selectBox;

						spacingData.margin.left.value = left.input;
						spacingData.margin.left.unit = left.selectBox;
					}

					portlet.setStyles(styling);
				};

				mTop.detach('blur');
				mTop.on('blur', changeMargin);

				mRight.detach('blur');
				mRight.on('blur', changeMargin);

				mBottom.detach('blur');
				mBottom.on('blur', changeMargin);

				mLeft.detach('blur');
				mLeft.on('blur', changeMargin);

				mTop.detach('keyup');
				mTop.on('keyup', changeMargin);

				mRight.detach('keyup');
				mRight.on('keyup', changeMargin);

				mBottom.detach('keyup');
				mBottom.on('keyup', changeMargin);

				mLeft.detach('keyup');
				mLeft.on('keyup', changeMargin);

				mTopUnit.detach('change');
				mTopUnit.on('change', changeMargin);

				mRightUnit.detach('change');
				mRightUnit.on('change', changeMargin);

				mBottomUnit.detach('change');
				mBottomUnit.on('change', changeMargin);

				mLeftUnit.detach('change');
				mLeftUnit.on('change', changeMargin);

				ufaMargin.detach('click');
				ufaMargin.on('click', changeMargin);
			},

			_textStyles: function() {
				var instance = this;

				var portlet = instance._curPortlet;
				var fontFamily = instance._fontFamily;
				var fontBold = instance._fontWeight;
				var fontItalic = instance._fontStyle;
				var fontSize = instance._fontSize;
				var fontColor = instance._fontColor;
				var textAlign = instance._textAlign;
				var textDecoration = instance._textDecoration;
				var wordSpacing = instance._wordSpacing;
				var leading = instance._leading;
				var tracking = instance._tracking;

				var textData = instance._objData.textData;

				// Font family

				fontFamily.detach('change');

				fontFamily.on(
					'change',
					function(event) {
						var fontFamily = event.currentTarget.val();

						portlet.setStyle('fontFamily', fontFamily);

						textData.fontFamily = fontFamily;
					}
				);

				// Font style

				fontBold.detach('click');

				fontBold.on(
					'click',
					function(event) {
						var style = 'normal';

						if (event.currentTarget.get('checked')) {
							style = 'bold';
						}

						portlet.setStyle('fontWeight', style);

						textData.fontWeight = style;
					}
				);

				fontItalic.detach('click');

				fontItalic.on(
					'click',
					function(event) {
						var style = 'normal';

						if (event.currentTarget.get('checked')) {
							style = 'italic';
						}

						portlet.setStyle('fontStyle', style);

						textData.fontStyle = style;
					}
				);

				// Font size

				fontSize.detach('change');

				fontSize.on(
					'change',
					function(event) {
						var fontSize = event.currentTarget.val();

						portlet.setStyle('fontSize', fontSize);

						textData.fontSize = fontSize;
					}
				);

				// Font color

				var changeColor = function(obj) {
					var color = obj.val();

					if (color) {
						portlet.setStyle('color', color);

						textData.color = color;
					}
				};

				var hexValue = fontColor.val().replace('#', '');

				if (!instance._fontColorPicker) {
					instance._fontColorPicker = new A.ColorPicker(
						{
							zIndex: 9999
						}
					).render();
				}

				var fontColorPicker = instance._fontColorPicker;

				var trigger = fontColorPicker.get('trigger');

				fontColor.placeAfter(trigger.item(0));

				var afterColorChange = function() {
					fontColor.val('#' + this.get('hex'));

					changeColor(fontColor);
				};

				if (instance._afterFontColorChangeHandler) {
					instance._afterFontColorChangeHandler.detach();
				}

				instance._afterFontColorChangeHandler = fontColorPicker.after('colorChange', afterColorChange);

				fontColorPicker.set('hex', hexValue);

				fontColor.detach('blur');

				fontColor.on(
					'blur',
					function(event) {
						changeColor(event.currentTarget);
					}
				);

				// Text alignment

				textAlign.detach('change');

				textAlign.on(
					'change',
					function(event) {
						var textAlign = event.currentTarget.val();

						portlet.setStyle('textAlign', textAlign);

						textData.textAlign = textAlign;
					}
				);

				// Text decoration

				textDecoration.detach('change');

				textDecoration.on(
					'change',
					function(event) {
						var decoration = event.currentTarget.val();

						portlet.setStyle('textDecoration', decoration);

						textData.textDecoration = decoration;
					}
				);

				// Word spacing

				wordSpacing.detach('change');

				wordSpacing.on(
					'change',
					function(event) {
						var spacing = event.currentTarget.val();

						portlet.setStyle('wordSpacing', spacing);

						textData.wordSpacing = spacing;
					}
				);

				// Line height

				leading.detach('change');

				leading.on(
					'change',
					function(event) {
						var leading = event.currentTarget.val();

						portlet.setStyle('lineHeight', leading);

						textData.lineHeight = leading;
					}
				);

				// Letter spacing

				tracking.detach('change');

				tracking.on(
					'change',
					function(event) {
						var tracking = event.currentTarget.val();

						portlet.setStyle('letterSpacing', tracking);

						textData.letterSpacing = tracking;
					}
				);
			}
		};

		Liferay.PortletCSS = PortletCSS;
	},
	'',
	{
		requires: ['color-picker', 'dialog', 'io', 'json', 'tabs'],
		use: []
	}
);