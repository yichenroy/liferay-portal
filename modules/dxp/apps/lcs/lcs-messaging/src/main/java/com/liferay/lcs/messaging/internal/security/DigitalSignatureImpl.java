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

package com.liferay.lcs.messaging.internal.security;

import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.Message;
import com.liferay.lcs.messaging.security.DigitalSignature;
import com.liferay.lcs.security.KeyStoreAdvisor;
import com.liferay.lcs.security.KeyStoreFactory;

import java.security.KeyStore;
import java.security.Signature;
import java.security.cert.Certificate;

import javax.xml.bind.DatatypeConverter;

import org.osgi.service.component.annotations.Component;

/**
 * Provides digital signing and verification methods. The LCS system is the
 * signer and uses a private key to produce the digital signature. The client
 * uses a public key to verify the digital signature.
 *
 * @author  Igor Beslic
 * @author  Ivica Cardic
 * @version 2.1.1
 * @since   LCS 0.1
 */
@Component(immediate = true, service = DigitalSignature.class)
public class DigitalSignatureImpl implements DigitalSignature {

	public DigitalSignatureImpl() {
		_keyStorePassword = "_k3y#5t0r3-p45S";
	}

	@Override
	public String getSignature(int buildNumber, String value) {
		try {
			return doGetSignature(buildNumber, value.getBytes());
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to sign value", e);
		}
	}

	/**
	 * Sets the key name.
	 *
	 * @param keyName the alias defined for key store key entry
	 * @since LCS 0.1
	 */
	public void setKeyName(String keyName) {
		_keyAlias = keyName;
	}

	/**
	 * Sets the key store password.
	 *
	 * @param keyStorePassword the key store password
	 * @since LCS 0.1
	 */
	public void setKeyStorePassword(String keyStorePassword) {
		_keyStorePassword = keyStorePassword;
	}

	/**
	 * Sets the key store path.
	 *
	 * @param keyStorePath the key store path
	 * @since LCS 0.1
	 */
	public void setKeyStorePath(String keyStorePath) {
		_keyStorePath = keyStorePath;
	}

	/**
	 * Sets the key store type.
	 *
	 * @param keyStoreType the key store type
	 * @since LCS 0.1
	 */
	public void setKeyStoreType(String keyStoreType) {
		_keyStoreType = keyStoreType;
	}

	/**
	 * Sets the signing algorithm.
	 *
	 * @param signingAlgorithm the signing algorithm
	 * @since LCS 0.1
	 */
	public void setSigningAlgorithm(String signingAlgorithm) {
		_signingAlgorithm = signingAlgorithm;
	}

	@Override
	public void signMessage(int buildNumber, Message message) {
		if (!(message instanceof CommandMessage)) {
			return;
		}

		CommandMessage commandMessage = (CommandMessage)message;

		try {
			commandMessage.setSignature(
				doGetSignature(buildNumber, getBytes(commandMessage)));
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to sign message", e);
		}
	}

	/**
	 * Signs the message, if possible.
	 *
	 * <p>
	 * If possible, this method produces a digital signature and stores it in
	 * the message's signature field {@link CommandMessage#_signature}.
	 * </p>
	 *
	 * @param message the message
	 * @since LCS 0.1
	 */
	@Override
	public void signMessage(Message message) {
		if (!(message instanceof CommandMessage)) {
			return;
		}

		CommandMessage commandMessage = (CommandMessage)message;

		try {
			commandMessage.setSignature(
				doGetSignature(0, getBytes(commandMessage)));
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to sign message", e);
		}
	}

	@Override
	public boolean verifyMessage(int buildNumber, Message message) {
		if (!(message instanceof CommandMessage)) {
			return true;
		}

		CommandMessage commandMessage = (CommandMessage)message;

		try {
			return doVerifyMessage(
				buildNumber, getBytes(commandMessage),
				commandMessage.getSignature());
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to verify message", e);
		}
	}

	/**
	 * Returns <code>true</code> if the message's digital signature is valid and
	 * it's a command message.
	 *
	 * @param  message the message
	 * @return <code>true</code> if the message's digital signature is valid and
	 *         it's a command message; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	@Override
	public boolean verifyMessage(Message message) {
		if (!(message instanceof CommandMessage)) {
			return true;
		}

		CommandMessage commandMessage = (CommandMessage)message;

		try {
			return doVerifyMessage(
				0, getBytes(commandMessage), commandMessage.getSignature());
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to verify message", e);
		}
	}

	@Override
	public boolean verifyValue(String value, String signature) {
		try {
			return doVerifyMessage(0, value.getBytes(), signature);
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to verify message", e);
		}
	}

	/**
	 * Signs the command message represented as bytes.
	 *
	 * @param  buildNumber the buildNumber
	 * @param  data the data
	 * @throws Exception if the entry does not contain the information needed to
	 *         recover the key (e.g. wrong password) or if an exception occurred
	 * @since  LCS 0.1
	 */
	protected String doGetSignature(int buildNumber, byte[] data)
		throws Exception {

		Signature signature = Signature.getInstance(_signingAlgorithm);

		KeyStore.ProtectionParameter protectionParameter =
			new KeyStore.PasswordProtection(_keyStorePassword.toCharArray());

		KeyStore keyStore = getKeyStore();

		String keyAlias = _keyStoreAdvisor.getKeyAlias(
			buildNumber, _keyAlias, keyStore);

		KeyStore.PrivateKeyEntry privateKeyEntry =
			(KeyStore.PrivateKeyEntry)keyStore.getEntry(
				keyAlias, protectionParameter);

		signature.initSign(privateKeyEntry.getPrivateKey());

		signature.update(data);

		return DatatypeConverter.printBase64Binary(signature.sign());
	}

	/**
	 * Returns <code>true</code> if the command message's digital signature is
	 * valid.
	 *
	 * @param  buildNumber the buildNumber
	 * @param  data the command message as bytes
	 * @param  signature the signature
	 * @return <code>true</code> if the command message's digital signature is
	 *         valid; <code>false</code> otherwise
	 * @throws Exception if the signature algorithm was unable to process the
	 *         input data provided, if the public key in the certificate did not
	 *         include required parameter information, or if an exception
	 *         occurred
	 * @since  LCS 0.1
	 */
	protected boolean doVerifyMessage(
			int buildNumber, byte[] data, String signature)
		throws Exception {

		Signature signatureInstance = Signature.getInstance(_signingAlgorithm);

		KeyStore keyStore = getKeyStore();

		String keyAlias = _keyStoreAdvisor.getKeyAlias(
			buildNumber, _keyAlias, keyStore);

		Certificate certificate = keyStore.getCertificate(keyAlias);

		signatureInstance.initVerify(certificate);

		signatureInstance.update(data);

		return signatureInstance.verify(
			DatatypeConverter.parseBase64Binary(signature));
	}

	/**
	 * Returns the command message value of the signaturesstring as bytes used
	 * for digital signing.
	 *
	 * @param  commandMessage the command message
	 * @return the command message's values as bytes
	 * @since  LCS 0.1
	 */
	protected byte[] getBytes(CommandMessage commandMessage) {
		String signatureString = commandMessage.getSignatureString();

		return signatureString.getBytes();
	}

	/**
	 * Returns the key store with the public and private keys.
	 *
	 * @return the key store with the public and private keys
	 * @throws Exception if the key store could not be initialized or if an
	 *         exception occurred
	 * @since  LCS 0.1
	 */
	protected KeyStore getKeyStore() throws Exception {
		if (_keyStore != null) {
			return _keyStore;
		}

		_keyStore = KeyStoreFactory.getInstance(
			_keyStorePath, _keyStoreType, _keyStorePassword);

		return _keyStore;
	}

	private String _keyAlias;
	private KeyStore _keyStore;
	private final KeyStoreAdvisor _keyStoreAdvisor = new KeyStoreAdvisor();
	private String _keyStorePassword;
	private String _keyStorePath;
	private String _keyStoreType;
	private String _signingAlgorithm;

}