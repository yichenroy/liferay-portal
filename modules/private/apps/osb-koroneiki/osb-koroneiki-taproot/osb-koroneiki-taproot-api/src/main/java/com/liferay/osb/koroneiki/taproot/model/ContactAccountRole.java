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
 * The extended model interface for the ContactAccountRole service. Represents a row in the &quot;Koroneiki_ContactAccountRole&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ContactAccountRoleModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.osb.koroneiki.taproot.model.impl.ContactAccountRoleImpl"
)
@ProviderType
public interface ContactAccountRole
	extends ContactAccountRoleModel, PersistedModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactAccountRoleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ContactAccountRole, Long> CONTACT_ID_ACCESSOR =
		new Accessor<ContactAccountRole, Long>() {

			@Override
			public Long get(ContactAccountRole contactAccountRole) {
				return contactAccountRole.getContactId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ContactAccountRole> getTypeClass() {
				return ContactAccountRole.class;
			}

		};
	public static final Accessor<ContactAccountRole, Long> ACCOUNT_ID_ACCESSOR =
		new Accessor<ContactAccountRole, Long>() {

			@Override
			public Long get(ContactAccountRole contactAccountRole) {
				return contactAccountRole.getAccountId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ContactAccountRole> getTypeClass() {
				return ContactAccountRole.class;
			}

		};
	public static final Accessor<ContactAccountRole, Long>
		CONTACT_ROLE_ID_ACCESSOR = new Accessor<ContactAccountRole, Long>() {

			@Override
			public Long get(ContactAccountRole contactAccountRole) {
				return contactAccountRole.getContactRoleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ContactAccountRole> getTypeClass() {
				return ContactAccountRole.class;
			}

		};

	public Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public Contact getContact()
		throws com.liferay.portal.kernel.exception.PortalException;

	public ContactRole getContactRole()
		throws com.liferay.portal.kernel.exception.PortalException;

}