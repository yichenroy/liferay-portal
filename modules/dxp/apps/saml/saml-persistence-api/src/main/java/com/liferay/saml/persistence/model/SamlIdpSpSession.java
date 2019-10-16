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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SamlIdpSpSession service. Represents a row in the &quot;SamlIdpSpSession&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlIdpSpSessionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.saml.persistence.model.impl.SamlIdpSpSessionImpl"
)
@ProviderType
public interface SamlIdpSpSession
	extends PersistedModel, SamlIdpSpSessionModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.saml.persistence.model.impl.SamlIdpSpSessionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlIdpSpSession, Long>
		SAML_IDP_SP_SESSION_ID_ACCESSOR =
			new Accessor<SamlIdpSpSession, Long>() {

				@Override
				public Long get(SamlIdpSpSession samlIdpSpSession) {
					return samlIdpSpSession.getSamlIdpSpSessionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SamlIdpSpSession> getTypeClass() {
					return SamlIdpSpSession.class;
				}

			};

}