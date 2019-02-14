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

package com.liferay.saml.persistence.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.service.SamlIdpSpSessionLocalServiceUtil;

/**
 * The extended model base implementation for the SamlIdpSpSession service. Represents a row in the &quot;SamlIdpSpSession&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SamlIdpSpSessionImpl}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlIdpSpSessionImpl
 * @see SamlIdpSpSession
 * @generated
 */
@ProviderType
public abstract class SamlIdpSpSessionBaseImpl extends SamlIdpSpSessionModelImpl
	implements SamlIdpSpSession {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a saml idp sp session model instance should use the <code>SamlIdpSpSession</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			SamlIdpSpSessionLocalServiceUtil.addSamlIdpSpSession(this);
		}
		else {
			SamlIdpSpSessionLocalServiceUtil.updateSamlIdpSpSession(this);
		}
	}
}