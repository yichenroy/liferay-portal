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

package com.liferay.osb.koroneiki.taproot.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ContactRole service. Represents a row in the &quot;Koroneiki_ContactRole&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ContactRoleModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.osb.koroneiki.taproot.model.impl.ContactRoleImpl"
)
@ProviderType
public interface ContactRole extends ContactRoleModel, PersistedModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactRoleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ContactRole, Long> CONTACT_ROLE_ID_ACCESSOR =
		new Accessor<ContactRole, Long>() {

			@Override
			public Long get(ContactRole contactRole) {
				return contactRole.getContactRoleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ContactRole> getTypeClass() {
				return ContactRole.class;
			}

		};
	public static final Accessor<ContactRole, String> NAME_ACCESSOR =
		new Accessor<ContactRole, String>() {

			@Override
			public String get(ContactRole contactRole) {
				return contactRole.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<ContactRole> getTypeClass() {
				return ContactRole.class;
			}

		};

	public String getTypeLabel();

	public boolean isMember();

}