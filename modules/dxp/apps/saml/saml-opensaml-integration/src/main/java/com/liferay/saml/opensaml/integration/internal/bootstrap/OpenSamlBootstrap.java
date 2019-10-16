/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.bootstrap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.apache.xml.security.stax.ext.XMLSecurityConstants;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.InitializationService;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;
import org.opensaml.xmlsec.signature.support.SignatureValidator;
import org.opensaml.xmlsec.signature.support.Signer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = OpenSamlBootstrap.class)
public class OpenSamlBootstrap {

	public static synchronized void bootstrap()
		throws IllegalAccessException, InitializationException,
			   InvocationTargetException, NoSuchMethodException {

		InitializationService.initialize();

		initializeParserPool();

		Method method = Signer.class.getDeclaredMethod("getSignerProvider");

		method.setAccessible(true);

		method.invoke(null);

		method = SignatureValidator.class.getDeclaredMethod(
			"getSignatureValidationProvider");

		method.setAccessible(true);

		method.invoke(null);

		if (XMLSecurityConstants.xmlOutputFactory == null) {
			throw new IllegalStateException();
		}
	}

	@Activate
	public synchronized void activate(BundleContext bundleContext)
		throws IllegalAccessException, InitializationException,
			   InvocationTargetException, NoSuchMethodException {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		try {
			Bundle bundle = bundleContext.getBundle();

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			currentThread.setContextClassLoader(bundleWiring.getClassLoader());

			bootstrap();

			XMLObjectProviderRegistry xmlObjectProviderRegistry =
				ConfigurationService.get(XMLObjectProviderRegistry.class);

			_parserPoolServiceRegistration = bundleContext.registerService(
				ParserPool.class, xmlObjectProviderRegistry.getParserPool(),
				null);
		}
		finally {
			currentThread.setContextClassLoader(classLoader);
		}
	}

	protected static void initializeParserPool()
		throws InitializationException {

		BasicParserPool parserPool = new BasicParserPool();

		Map<String, Boolean> builderFeatures = new HashMap<>();

		builderFeatures.put(
			"http://apache.org/xml/features/disallow-doctype-decl",
			Boolean.TRUE);
		builderFeatures.put(
			"http://apache.org/xml/features/dom/defer-node-expansion",
			Boolean.FALSE);
		builderFeatures.put(
			"http://javax.xml.XMLConstants/feature/secure-processing",
			Boolean.TRUE);
		builderFeatures.put(
			"http://xml.org/sax/features/external-general-entities",
			Boolean.FALSE);
		builderFeatures.put(
			"http://xml.org/sax/features/external-parameter-entities",
			Boolean.FALSE);

		parserPool.setBuilderFeatures(builderFeatures);

		parserPool.setDTDValidating(false);
		parserPool.setExpandEntityReferences(false);
		parserPool.setNamespaceAware(true);
		parserPool.setMaxPoolSize(50);

		try {
			parserPool.initialize();

			parserPool.getBuilder();

			XMLObjectProviderRegistry xmlObjectProviderRegistry =
				ConfigurationService.get(XMLObjectProviderRegistry.class);

			xmlObjectProviderRegistry.setParserPool(parserPool);
		}
		catch (Exception xmlpe) {
			throw new InitializationException(
				"Unable to initialize parser pool: " + xmlpe.getMessage(),
				xmlpe);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_parserPoolServiceRegistration != null) {
			_parserPoolServiceRegistration.unregister();
		}
	}

	private ServiceRegistration<ParserPool> _parserPoolServiceRegistration;

}