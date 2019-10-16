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

package com.liferay.saml.opensaml.integration.internal.credential;

import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.credential.KeyStoreManager;
import com.liferay.saml.runtime.metadata.LocalEntityManager;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;

import org.apache.xml.security.utils.Base64;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.credential.impl.AbstractCredentialResolver;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.X509Credential;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlKeyStoreManagerConfiguration",
	immediate = true,
	service = {CredentialResolver.class, LocalEntityManager.class}
)
public class KeyStoreCredentialResolver
	extends AbstractCredentialResolver implements LocalEntityManager {

	@Override
	public void deleteLocalEntityCertificate(CertificateUsage certificateUsage)
		throws KeyStoreException {

		KeyStore keyStore = _keyStoreManager.getKeyStore();

		keyStore.deleteEntry(
			getAlias(getLocalEntityId(), getUsageType(certificateUsage)));
	}

	@Override
	public String getEncodedLocalEntityCertificate(
			CertificateUsage certificateUsage)
		throws SamlException {

		try {
			X509Certificate x509Certificate = getLocalEntityCertificate(
				certificateUsage);

			if (x509Certificate == null) {
				return null;
			}

			return Base64.encode(x509Certificate.getEncoded(), 76);
		}
		catch (CertificateEncodingException cee) {
			throw new SamlException(cee);
		}
	}

	@Override
	public X509Certificate getLocalEntityCertificate(
			CertificateUsage certificateUsage)
		throws SamlException {

		UsageType usageType = getUsageType(certificateUsage);

		if (usageType == null) {
			return null;
		}

		UsageCriterion usageCriterion = new UsageCriterion(usageType);

		try {
			X509Credential x509Credential = (X509Credential)resolveSingle(
				new CriteriaSet(
					new EntityIdCriterion(getLocalEntityId()), usageCriterion));

			if (x509Credential == null) {
				return null;
			}

			return x509Credential.getEntityCertificate();
		}
		catch (ResolverException re) {
			throw new SamlException(re);
		}
	}

	@Override
	public String getLocalEntityId() {
		return getSamlProviderConfiguration().entityId();
	}

	@Override
	public boolean hasDefaultIdpRole() {
		List<SamlSpIdpConnection> samlSpIdpConnections =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnections(
				CompanyThreadLocal.getCompanyId());

		if (samlSpIdpConnections.isEmpty()) {
			return false;
		}

		return true;
	}

	@Override
	public Iterable<Credential> resolve(CriteriaSet criteriaSet)
		throws SecurityException {

		try {
			checkCriteriaRequirements(criteriaSet);

			EntityIdCriterion entityIDCriterion = criteriaSet.get(
				EntityIdCriterion.class);

			String entityId = entityIDCriterion.getEntityId();

			KeyStore.PasswordProtection keyStorePasswordProtection = null;

			SamlProviderConfiguration samlProviderConfiguration =
				_samlProviderConfigurationHelper.getSamlProviderConfiguration();

			UsageCriterion usageCriterion = criteriaSet.get(
				UsageCriterion.class);

			UsageType usageType = UsageType.UNSPECIFIED;

			if (usageCriterion != null) {
				usageType = usageCriterion.getUsage();
			}

			if (entityId.equals(samlProviderConfiguration.entityId())) {
				String keyStoreCredentialPassword = null;

				if (usageType == UsageType.ENCRYPTION) {
					keyStoreCredentialPassword =
						samlProviderConfiguration.
							keyStoreEncryptionCredentialPassword();
				}
				else {
					keyStoreCredentialPassword =
						samlProviderConfiguration.keyStoreCredentialPassword();
				}

				if (keyStoreCredentialPassword != null) {
					keyStorePasswordProtection =
						new KeyStore.PasswordProtection(
							keyStoreCredentialPassword.toCharArray());
				}
			}

			KeyStore keyStore = _keyStoreManager.getKeyStore();

			KeyStore.Entry entry = keyStore.getEntry(
				getAlias(entityId, usageType), keyStorePasswordProtection);

			if (entry == null) {
				return Collections.emptySet();
			}

			Credential credential = buildCredential(entry, entityId, usageType);

			return Collections.singleton(credential);
		}
		catch (RuntimeException re) {
			throw new SecurityException(re);
		}
		catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	@Reference(
		name = "KeyStoreManager", target = "(default=true)", unbind = "-"
	)
	public void setKeyStoreManager(KeyStoreManager keyStoreManager) {
		_keyStoreManager = keyStoreManager;
	}

	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		_samlProviderConfigurationHelper = samlProviderConfigurationHelper;
	}

	@Override
	public void storeLocalEntityCertificate(
			PrivateKey privateKey, String certificateKeyPassword,
			X509Certificate x509Certificate, CertificateUsage certificateUsage)
		throws Exception {

		KeyStore keyStore = _keyStoreManager.getKeyStore();

		keyStore.setEntry(
			getAlias(
				getLocalEntityId(), getUsageType(certificateUsage)),
			new KeyStore.PrivateKeyEntry(
				privateKey, new Certificate[] {x509Certificate}),
			new KeyStore.PasswordProtection(
				certificateKeyPassword.toCharArray()));

		_keyStoreManager.saveKeyStore(keyStore);
	}

	protected Credential buildCredential(
		KeyStore.Entry entry, String entityId, UsageType usage) {

		if (entry instanceof KeyStore.PrivateKeyEntry) {
			return processPrivateKeyEntry(
				(KeyStore.PrivateKeyEntry)entry, entityId, usage);
		}
		else if (entry instanceof KeyStore.SecretKeyEntry) {
			return processSecretKeyEntry(
				(KeyStore.SecretKeyEntry)entry, entityId, usage);
		}
		else if (entry instanceof KeyStore.TrustedCertificateEntry) {
			return processTrustedCertificateEntry(
				(KeyStore.TrustedCertificateEntry)entry, entityId, usage);
		}

		return null;
	}

	protected void checkCriteriaRequirements(CriteriaSet criteriaSet) {
		EntityIdCriterion entityIdCriterion = criteriaSet.get(
			EntityIdCriterion.class);

		if (entityIdCriterion == null) {
			throw new IllegalArgumentException(
				"No entity ID criterion was available in criteria set");
		}
	}

	protected String getAlias(String entityId, UsageType usageType) {
		if (usageType.equals(UsageType.SIGNING)) {
			return entityId;
		}
		else if (usageType.equals(UsageType.ENCRYPTION)) {
			return entityId + "-encryption";
		}

		return entityId;
	}

	protected SamlProviderConfiguration getSamlProviderConfiguration() {
		return _samlProviderConfigurationHelper.getSamlProviderConfiguration();
	}

	protected UsageType getUsageType(CertificateUsage certificateUsage) {
		UsageType usageType = null;

		if (certificateUsage == CertificateUsage.ENCRYPTION) {
			usageType = UsageType.ENCRYPTION;
		}
		else if (certificateUsage == CertificateUsage.SIGNING) {
			usageType = UsageType.SIGNING;
		}

		return usageType;
	}

	protected Credential processPrivateKeyEntry(
		KeyStore.PrivateKeyEntry privateKeyEntry, String entityId,
		UsageType usageType) {

		BasicX509Credential basicX509Credential = new BasicX509Credential(
			(X509Certificate)privateKeyEntry.getCertificate());

		basicX509Credential.setEntityCertificateChain(
			Arrays.asList(
				(X509Certificate[])privateKeyEntry.getCertificateChain()));
		basicX509Credential.setEntityId(entityId);
		basicX509Credential.setPrivateKey(privateKeyEntry.getPrivateKey());
		basicX509Credential.setUsageType(usageType);

		return basicX509Credential;
	}

	protected Credential processSecretKeyEntry(
		KeyStore.SecretKeyEntry secretKeyEntry, String entityId,
		UsageType usageType) {

		BasicCredential basicCredential = new BasicCredential(
			secretKeyEntry.getSecretKey());

		basicCredential.setEntityId(entityId);
		basicCredential.setUsageType(usageType);

		return basicCredential;
	}

	protected Credential processTrustedCertificateEntry(
		KeyStore.TrustedCertificateEntry trustedCertificateEntry,
		String entityId, UsageType usageType) {

		X509Certificate x509Certificate =
			(X509Certificate)trustedCertificateEntry.getTrustedCertificate();

		BasicX509Credential basicX509Credential = new BasicX509Credential(
			x509Certificate);

		List<X509Certificate> x509Certificates = new ArrayList<>();

		x509Certificates.add(x509Certificate);

		basicX509Credential.setEntityCertificateChain(x509Certificates);

		basicX509Credential.setEntityId(entityId);
		basicX509Credential.setUsageType(usageType);

		return basicX509Credential;
	}

	private KeyStoreManager _keyStoreManager;
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}