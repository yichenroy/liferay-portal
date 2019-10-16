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

package com.liferay.oauth.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the OAuthUser service. Represents a row in the &quot;OAuth_OAuthUser&quot; database table, with each column mapped to a property of this class.
 *
 * @author Ivica Cardic
 * @see OAuthUserModel
 * @see com.liferay.oauth.model.impl.OAuthUserImpl
 * @see com.liferay.oauth.model.impl.OAuthUserModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.oauth.model.impl.OAuthUserImpl")
@ProviderType
public interface OAuthUser extends OAuthUserModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.oauth.model.impl.OAuthUserImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OAuthUser, Long> O_AUTH_USER_ID_ACCESSOR = new Accessor<OAuthUser, Long>() {
			@Override
			public Long get(OAuthUser oAuthUser) {
				return oAuthUser.getOAuthUserId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<OAuthUser> getTypeClass() {
				return OAuthUser.class;
			}
		};
}