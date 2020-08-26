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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.configuration.FragmentServiceConfiguration;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class EditFragmentEntryDisplayContext {

	public EditFragmentEntryDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)
				_httpServletRequest.getAttribute(
					FragmentWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
		_fragmentEntryProcessorRegistry =
			(FragmentEntryProcessorRegistry)_httpServletRequest.getAttribute(
				FragmentWebKeys.FRAGMENT_ENTRY_PROCESSOR_REGISTRY);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_setViewAttributes();
	}

	public long getFragmentCollectionId() {
		if (Validator.isNotNull(_fragmentCollectionId)) {
			return _fragmentCollectionId;
		}

		long defaultFragmentCollectionId = 0;

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_themeDisplay.getScopeGroupId(), 0, 1);

		if (ListUtil.isNotEmpty(fragmentCollections)) {
			FragmentCollection fragmentCollection = fragmentCollections.get(0);

			defaultFragmentCollectionId =
				fragmentCollection.getFragmentCollectionId();
		}

		_fragmentCollectionId = ParamUtil.getLong(
			_httpServletRequest, "fragmentCollectionId",
			defaultFragmentCollectionId);

		return _fragmentCollectionId;
	}

	public Map<String, Object> getFragmentEditorData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"context",
			Collections.singletonMap(
				"namespace", _renderResponse.getNamespace())
		).put(
			"props", _getProps()
		).build();
	}

	public FragmentEntry getFragmentEntry() {
		if (_fragmentEntry != null) {
			return _fragmentEntry;
		}

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				getFragmentEntryId());

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				getFragmentCollectionId());

		if ((fragmentEntry == null) && (fragmentCollection != null)) {
			fragmentEntry = FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentCollection.getGroupId(), getFragmentEntryKey());
		}

		if (fragmentEntry == null) {
			fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					getFragmentEntryKey());
		}

		_fragmentEntry = fragmentEntry;

		return _fragmentEntry;
	}

	public long getFragmentEntryId() {
		if (Validator.isNotNull(_fragmentEntryId)) {
			return _fragmentEntryId;
		}

		long fragmentEntryId = ParamUtil.getLong(
			_httpServletRequest, "fragmentEntryId");

		FragmentEntry draftFragmentEntry =
			FragmentEntryLocalServiceUtil.fetchDraft(fragmentEntryId);

		if (draftFragmentEntry == null) {
			_fragmentEntryId = fragmentEntryId;
		}
		else {
			_fragmentEntryId = draftFragmentEntry.getFragmentEntryId();
		}

		return _fragmentEntryId;
	}

	public String getFragmentEntryKey() {
		if (Validator.isNotNull(_fragmentEntryKey)) {
			return _fragmentEntryKey;
		}

		_fragmentEntryKey = ParamUtil.getString(
			_httpServletRequest, "fragmentEntryKey");

		return _fragmentEntryKey;
	}

	public String getFragmentEntryTitle() {
		FragmentEntry fragmentEntry = getFragmentEntry();

		if (fragmentEntry == null) {
			return LanguageUtil.get(_httpServletRequest, "add-fragment");
		}

		return fragmentEntry.getName();
	}

	public String getName() {
		if (Validator.isNotNull(_name)) {
			return _name;
		}

		_name = ParamUtil.getString(_httpServletRequest, "name");

		FragmentEntry fragmentEntry = getFragmentEntry();

		if ((fragmentEntry != null) && Validator.isNull(_name)) {
			_name = fragmentEntry.getName();
		}

		return _name;
	}

	public String getRedirect() {
		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/fragment/view");

		if (getFragmentCollectionId() > 0) {
			portletURL.setParameter(
				"fragmentCollectionId",
				String.valueOf(getFragmentCollectionId()));
		}

		return portletURL.toString();
	}

	private String _getConfigurationContent() {
		if (Validator.isNotNull(_configurationContent)) {
			return _configurationContent;
		}

		_configurationContent = ParamUtil.getString(
			_httpServletRequest, "configurationContent");

		FragmentEntry fragmentEntry = getFragmentEntry();

		if ((fragmentEntry != null) &&
			Validator.isNull(_configurationContent)) {

			_configurationContent = fragmentEntry.getConfiguration();

			if (Validator.isNull(_configurationContent)) {
				_configurationContent = "{\n\t\"fieldSets\": [\n\t]\n}";
			}
		}

		return _configurationContent;
	}

	private String _getCssContent() {
		if (Validator.isNotNull(_cssContent)) {
			return _cssContent;
		}

		FragmentEntry fragmentEntry = getFragmentEntry();

		if (fragmentEntry != null) {
			_cssContent = fragmentEntry.getCss();
		}

		return _cssContent;
	}

	private String _getFragmentEntryRenderURL(String mvcRenderCommandName)
		throws Exception {

		PortletURL portletURL = PortletURLFactoryUtil.create(
			_httpServletRequest, FragmentPortletKeys.FRAGMENT,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);

		FragmentEntry fragmentEntry = getFragmentEntry();

		portletURL.setParameter(
			"fragmentEntryId",
			String.valueOf(fragmentEntry.getFragmentEntryId()));
		portletURL.setParameter(
			"fragmentEntryKey",
			String.valueOf(fragmentEntry.getFragmentEntryKey()));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private String _getHtmlContent() {
		if (Validator.isNotNull(_htmlContent)) {
			return _htmlContent;
		}

		FragmentEntry fragmentEntry = getFragmentEntry();

		if (fragmentEntry != null) {
			_htmlContent = fragmentEntry.getHtml();
		}

		return _htmlContent;
	}

	private String _getJsContent() {
		if (Validator.isNotNull(_jsContent)) {
			return _jsContent;
		}

		FragmentEntry fragmentEntry = getFragmentEntry();

		if (fragmentEntry != null) {
			_jsContent = fragmentEntry.getJs();
		}

		return _jsContent;
	}

	private Map<String, Object> _getProps() throws Exception {
		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(
				TemplateConstants.LANG_TYPE_FTL);

		Template template = templateManager.getTemplate(
			new StringTemplateResource(
				TemplateConstants.LANG_TYPE_FTL,
				TemplateConstants.LANG_TYPE_FTL),
			true);

		template.prepare(_httpServletRequest);

		Set<String> originalKeys = new HashSet<>(template.keySet());

		template.prepareTaglib(
			_httpServletRequest,
			PortalUtil.getHttpServletResponse(_renderResponse));

		Set<String> taglibKeys = new HashSet<>(template.keySet());

		taglibKeys.removeAll(originalKeys);

		List<String> freeMarkerTaglibs = new ArrayList<>(taglibKeys);

		List<String> freeMarkerVariables = new ArrayList<>(template.keySet());

		freeMarkerVariables.add("configuration");

		FragmentCollection fragmentCollection =
			FragmentCollectionServiceUtil.fetchFragmentCollection(
				getFragmentCollectionId());

		List<String> resources = new ArrayList<>();

		if (fragmentCollection != null) {
			for (FileEntry fileEntry : fragmentCollection.getResources()) {
				resources.add(fileEntry.getFileName());
			}
		}

		return HashMapBuilder.<String, Object>put(
			"allowedStatus",
			HashMapBuilder.<String, Object>put(
				"approved", String.valueOf(WorkflowConstants.STATUS_APPROVED)
			).put(
				"draft", String.valueOf(WorkflowConstants.STATUS_DRAFT)
			).build()
		).put(
			"autocompleteTags",
			_fragmentEntryProcessorRegistry.getAvailableTagsJSONArray()
		).put(
			"cacheable", _fragmentEntry.isCacheable()
		).put(
			"dataAttributes",
			_fragmentEntryProcessorRegistry.getDataAttributesJSONArray()
		).put(
			"fragmentCollectionId", getFragmentCollectionId()
		).put(
			"fragmentEntryId", getFragmentEntryId()
		).put(
			"freeMarkerTaglibs", freeMarkerTaglibs
		).put(
			"freeMarkerVariables", freeMarkerVariables
		).put(
			"htmlEditorCustomEntities",
			() -> {
				List<Map<String, Object>> htmlEditorCustomEntities =
					new ArrayList<>();

				htmlEditorCustomEntities.add(
					HashMapBuilder.<String, Object>put(
						"content", freeMarkerTaglibs
					).put(
						"end", "]"
					).put(
						"start", "[@"
					).build());

				htmlEditorCustomEntities.add(
					HashMapBuilder.<String, Object>put(
						"content", freeMarkerVariables
					).put(
						"end", "}"
					).put(
						"start", "${"
					).build());

				htmlEditorCustomEntities.add(
					HashMapBuilder.<String, Object>put(
						"content", resources
					).put(
						"end", "]"
					).put(
						"start", "[resources:"
					).build());

				return htmlEditorCustomEntities;
			}
		).put(
			"initialConfiguration", _getConfigurationContent()
		).put(
			"initialCSS", _getCssContent()
		).put(
			"initialHTML", _getHtmlContent()
		).put(
			"initialJS", _getJsContent()
		).put(
			"name", getName()
		).put(
			"portletNamespace", _renderResponse.getNamespace()
		).put(
			"propagationEnabled",
			() -> {
				FragmentServiceConfiguration fragmentServiceConfiguration =
					ConfigurationProviderUtil.getCompanyConfiguration(
						FragmentServiceConfiguration.class,
						_themeDisplay.getCompanyId());

				return fragmentServiceConfiguration.propagateChanges();
			}
		).put(
			"readOnly", _isReadOnlyFragmentEntry()
		).put(
			"resources", resources
		).put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
		).put(
			"status",
			() -> {
				FragmentEntry fragmentEntry = getFragmentEntry();

				return String.valueOf(fragmentEntry.getStatus());
			}
		).put(
			"urls",
			HashMapBuilder.<String, Object>put(
				"current", _themeDisplay.getURLCurrent()
			).put(
				"edit",
				() -> {
					PortletURL editActionURL =
						_renderResponse.createActionURL();

					editActionURL.setParameter(
						ActionRequest.ACTION_NAME,
						"/fragment/edit_fragment_entry");

					return editActionURL.toString();
				}
			).put(
				"preview",
				_getFragmentEntryRenderURL("/fragment/preview_fragment_entry")
			).put(
				"publish", _getPublishFragmentEntryActionURL()
			).put(
				"redirect", getRedirect()
			).put(
				"render",
				_getFragmentEntryRenderURL("/fragment/render_fragment_entry")
			).build()
		).build();
	}

	private String _getPublishFragmentEntryActionURL() {
		PortletURL publishFragmentEntryURL = PortletURLFactoryUtil.create(
			_httpServletRequest, FragmentPortletKeys.FRAGMENT,
			PortletRequest.ACTION_PHASE);

		publishFragmentEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/publish_fragment_entry");
		publishFragmentEntryURL.setParameter(
			"fragmentEntryId", String.valueOf(getFragmentEntryId()));

		return publishFragmentEntryURL.toString();
	}

	private boolean _isReadOnlyFragmentEntry() {
		if (_readOnly != null) {
			return _readOnly;
		}

		FragmentEntry fragmentEntry = getFragmentEntry();

		if (fragmentEntry.isReadOnly()) {
			_readOnly = true;

			return _readOnly;
		}

		boolean readOnly = false;

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				getFragmentCollectionId());

		if ((fragmentCollection == null) ||
			(fragmentCollection.getGroupId() !=
				_themeDisplay.getScopeGroupId())) {

			readOnly = true;
		}

		_readOnly = readOnly;

		return _readOnly;
	}

	private void _setViewAttributes() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(getRedirect());

		FragmentEntry fragmentEntry = getFragmentEntry();

		if (WorkflowConstants.STATUS_DRAFT != fragmentEntry.getStatus()) {
			_renderResponse.setTitle(getFragmentEntryTitle());

			return;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(getFragmentEntryTitle());
		sb.append(" (");
		sb.append(
			LanguageUtil.get(
				_httpServletRequest,
				WorkflowConstants.getStatusLabel(fragmentEntry.getStatus())));
		sb.append(")");

		_renderResponse.setTitle(sb.toString());
	}

	private String _configurationContent;
	private String _cssContent;
	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private Long _fragmentCollectionId;
	private FragmentEntry _fragmentEntry;
	private Long _fragmentEntryId;
	private String _fragmentEntryKey;
	private final FragmentEntryProcessorRegistry
		_fragmentEntryProcessorRegistry;
	private String _htmlContent;
	private final HttpServletRequest _httpServletRequest;
	private String _jsContent;
	private String _name;
	private Boolean _readOnly;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}