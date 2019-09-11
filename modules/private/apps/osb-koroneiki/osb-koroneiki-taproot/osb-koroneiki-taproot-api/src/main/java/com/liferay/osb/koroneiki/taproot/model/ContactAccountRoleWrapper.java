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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ContactAccountRole}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ContactAccountRole
 * @generated
 */
public class ContactAccountRoleWrapper
	extends BaseModelWrapper<ContactAccountRole>
	implements ContactAccountRole, ModelWrapper<ContactAccountRole> {

	public ContactAccountRoleWrapper(ContactAccountRole contactAccountRole) {
		super(contactAccountRole);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("contactId", getContactId());
		attributes.put("accountId", getAccountId());
		attributes.put("contactRoleId", getContactRoleId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long contactId = (Long)attributes.get("contactId");

		if (contactId != null) {
			setContactId(contactId);
		}

		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
		}

		Long contactRoleId = (Long)attributes.get("contactRoleId");

		if (contactRoleId != null) {
			setContactRoleId(contactRoleId);
		}
	}

	@Override
	public Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAccount();
	}

	/**
	 * Returns the account ID of this contact account role.
	 *
	 * @return the account ID of this contact account role
	 */
	@Override
	public long getAccountId() {
		return model.getAccountId();
	}

	@Override
	public Contact getContact()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getContact();
	}

	/**
	 * Returns the contact ID of this contact account role.
	 *
	 * @return the contact ID of this contact account role
	 */
	@Override
	public long getContactId() {
		return model.getContactId();
	}

	@Override
	public ContactRole getContactRole()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getContactRole();
	}

	/**
	 * Returns the contact role ID of this contact account role.
	 *
	 * @return the contact role ID of this contact account role
	 */
	@Override
	public long getContactRoleId() {
		return model.getContactRoleId();
	}

	/**
	 * Returns the primary key of this contact account role.
	 *
	 * @return the primary key of this contact account role
	 */
	@Override
	public
		com.liferay.osb.koroneiki.taproot.service.persistence.
			ContactAccountRolePK getPrimaryKey() {

		return model.getPrimaryKey();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a contact account role model instance should use the <code>ContactAccountRole</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account ID of this contact account role.
	 *
	 * @param accountId the account ID of this contact account role
	 */
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	 * Sets the contact ID of this contact account role.
	 *
	 * @param contactId the contact ID of this contact account role
	 */
	@Override
	public void setContactId(long contactId) {
		model.setContactId(contactId);
	}

	/**
	 * Sets the contact role ID of this contact account role.
	 *
	 * @param contactRoleId the contact role ID of this contact account role
	 */
	@Override
	public void setContactRoleId(long contactRoleId) {
		model.setContactRoleId(contactRoleId);
	}

	/**
	 * Sets the primary key of this contact account role.
	 *
	 * @param primaryKey the primary key of this contact account role
	 */
	@Override
	public void setPrimaryKey(
		com.liferay.osb.koroneiki.taproot.service.persistence.
			ContactAccountRolePK primaryKey) {

		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected ContactAccountRoleWrapper wrap(
		ContactAccountRole contactAccountRole) {

		return new ContactAccountRoleWrapper(contactAccountRole);
	}

}