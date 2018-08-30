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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.template.TemplateResourceThreadLocal;
import com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration;

import freemarker.cache.TemplateCache;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

import java.lang.reflect.Constructor;

import java.util.Locale;

/**
 * @author Tina Tian
 */
public class LiferayTemplateCache extends TemplateCache {

	public LiferayTemplateCache(
			Configuration configuration,
			FreeMarkerEngineConfiguration freeMarkerEngineConfiguration,
			TemplateResourceLoader templateResourceLoader,
			SingleVMPool singleVMPool)
		throws Exception {

		super(null, configuration);

		_configuration = configuration;
		_freeMarkerEngineConfiguration = freeMarkerEngineConfiguration;
		_templateResourceLoader = templateResourceLoader;

		String porttalCacheName = TemplateResource.class.getName();

		porttalCacheName = porttalCacheName.concat(StringPool.POUND).concat(
			TemplateConstants.LANG_TYPE_FTL);

		_portalCache =
			(PortalCache<TemplateResource, Object>)singleVMPool.getPortalCache(
				porttalCacheName);

		_constructor =
			TemplateCache.MaybeMissingTemplate.class.getDeclaredConstructor(
				Template.class);

		_constructor.setAccessible(true);
	}

	@Override
	public TemplateCache.MaybeMissingTemplate getTemplate(
			String templateId, Locale locale, Object customLookupCondition,
			String encoding, boolean parse)
		throws IOException {

		if (templateId == null) {
			throw new IllegalArgumentException("Argument \"name\" is null");
		}

		if (locale == null) {
			throw new IllegalArgumentException("Argument \"locale\" is null");
		}

		if (encoding == null) {
			throw new IllegalArgumentException("Argument \"encoding\" is null");
		}

		TemplateResource templateResource = null;

		if (templateId.startsWith(
				TemplateConstants.TEMPLATE_RESOURCE_UUID_PREFIX)) {

			templateResource = TemplateResourceThreadLocal.getTemplateResource(
				TemplateConstants.LANG_TYPE_FTL);
		}
		else {
			try {
				templateResource = _templateResourceLoader.getTemplateResource(
					templateId);
			}
			catch (Exception e) {
				templateResource = null;
			}
		}

		if (templateResource == null) {
			throw new IOException(
				"Unable to find FreeMarker template with ID " + templateId);
		}

		Object object = _portalCache.get(templateResource);

		if ((object != null) &&
			(object instanceof TemplateCache.MaybeMissingTemplate)) {

			return (TemplateCache.MaybeMissingTemplate)object;
		}

		Template template = new Template(
			templateResource.getTemplateId(), templateResource.getReader(),
			_configuration);

		try {
			TemplateCache.MaybeMissingTemplate maybeMissingTemplate =
				_constructor.newInstance(template);

			if (_freeMarkerEngineConfiguration.resourceModificationCheck() !=
					0) {

				_portalCache.put(templateResource, maybeMissingTemplate);
			}

			return maybeMissingTemplate;
		}
		catch (ReflectiveOperationException roe) {
			throw new IOException(roe);
		}
	}

	private final Configuration _configuration;
	private final Constructor<TemplateCache.MaybeMissingTemplate> _constructor;
	private final FreeMarkerEngineConfiguration _freeMarkerEngineConfiguration;
	private final PortalCache<TemplateResource, Object> _portalCache;
	private final TemplateResourceLoader _templateResourceLoader;

}