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

import com.liferay.osb.koroneiki.taproot.service.persistence.ContactAccountRolePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.osb.koroneiki.taproot.service.http.ContactAccountRoleServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ContactAccountRoleSoap implements Serializable {

	public static ContactAccountRoleSoap toSoapModel(ContactAccountRole model) {
		ContactAccountRoleSoap soapModel = new ContactAccountRoleSoap();

		soapModel.setContactId(model.getContactId());
		soapModel.setAccountId(model.getAccountId());
		soapModel.setContactRoleId(model.getContactRoleId());

		return soapModel;
	}

	public static ContactAccountRoleSoap[] toSoapModels(
		ContactAccountRole[] models) {

		ContactAccountRoleSoap[] soapModels =
			new ContactAccountRoleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ContactAccountRoleSoap[][] toSoapModels(
		ContactAccountRole[][] models) {

		ContactAccountRoleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ContactAccountRoleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ContactAccountRoleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ContactAccountRoleSoap[] toSoapModels(
		List<ContactAccountRole> models) {

		List<ContactAccountRoleSoap> soapModels =
			new ArrayList<ContactAccountRoleSoap>(models.size());

		for (ContactAccountRole model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new ContactAccountRoleSoap[soapModels.size()]);
	}

	public ContactAccountRoleSoap() {
	}

	public ContactAccountRolePK getPrimaryKey() {
		return new ContactAccountRolePK(_contactId, _accountId, _contactRoleId);
	}

	public void setPrimaryKey(ContactAccountRolePK pk) {
		setContactId(pk.contactId);
		setAccountId(pk.accountId);
		setContactRoleId(pk.contactRoleId);
	}

	public long getContactId() {
		return _contactId;
	}

	public void setContactId(long contactId) {
		_contactId = contactId;
	}

	public long getAccountId() {
		return _accountId;
	}

	public void setAccountId(long accountId) {
		_accountId = accountId;
	}

	public long getContactRoleId() {
		return _contactRoleId;
	}

	public void setContactRoleId(long contactRoleId) {
		_contactRoleId = contactRoleId;
	}

	private long _contactId;
	private long _accountId;
	private long _contactRoleId;

}