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
 * The extended model interface for the ContactTeamRole service. Represents a row in the &quot;Koroneiki_ContactTeamRole&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ContactTeamRoleModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.osb.koroneiki.taproot.model.impl.ContactTeamRoleImpl"
)
@ProviderType
public interface ContactTeamRole extends ContactTeamRoleModel, PersistedModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactTeamRoleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ContactTeamRole, Long> CONTACT_ID_ACCESSOR =
		new Accessor<ContactTeamRole, Long>() {

			@Override
			public Long get(ContactTeamRole contactTeamRole) {
				return contactTeamRole.getContactId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ContactTeamRole> getTypeClass() {
				return ContactTeamRole.class;
			}

		};
	public static final Accessor<ContactTeamRole, Long> TEAM_ID_ACCESSOR =
		new Accessor<ContactTeamRole, Long>() {

			@Override
			public Long get(ContactTeamRole contactTeamRole) {
				return contactTeamRole.getTeamId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ContactTeamRole> getTypeClass() {
				return ContactTeamRole.class;
			}

		};
	public static final Accessor<ContactTeamRole, Long>
		CONTACT_ROLE_ID_ACCESSOR = new Accessor<ContactTeamRole, Long>() {

			@Override
			public Long get(ContactTeamRole contactTeamRole) {
				return contactTeamRole.getContactRoleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ContactTeamRole> getTypeClass() {
				return ContactTeamRole.class;
			}

		};

}