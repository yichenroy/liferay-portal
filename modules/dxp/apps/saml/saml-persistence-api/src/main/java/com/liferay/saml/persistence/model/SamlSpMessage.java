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

package com.liferay.saml.persistence.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the SamlSpMessage service. Represents a row in the &quot;SamlSpMessage&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlSpMessageModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.saml.persistence.model.impl.SamlSpMessageImpl"
)
@ProviderType
public interface SamlSpMessage extends PersistedModel, SamlSpMessageModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.saml.persistence.model.impl.SamlSpMessageImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlSpMessage, Long>
		SAML_SP_MESSAGE_ID_ACCESSOR = new Accessor<SamlSpMessage, Long>() {

			@Override
			public Long get(SamlSpMessage samlSpMessage) {
				return samlSpMessage.getSamlSpMessageId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SamlSpMessage> getTypeClass() {
				return SamlSpMessage.class;
			}

		};

	public boolean isExpired();

}