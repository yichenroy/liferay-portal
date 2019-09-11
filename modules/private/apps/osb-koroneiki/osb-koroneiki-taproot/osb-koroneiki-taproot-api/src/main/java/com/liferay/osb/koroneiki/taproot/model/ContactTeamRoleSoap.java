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

import com.liferay.osb.koroneiki.taproot.service.persistence.ContactTeamRolePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.osb.koroneiki.taproot.service.http.ContactTeamRoleServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ContactTeamRoleSoap implements Serializable {

	public static ContactTeamRoleSoap toSoapModel(ContactTeamRole model) {
		ContactTeamRoleSoap soapModel = new ContactTeamRoleSoap();

		soapModel.setContactId(model.getContactId());
		soapModel.setTeamId(model.getTeamId());
		soapModel.setContactRoleId(model.getContactRoleId());

		return soapModel;
	}

	public static ContactTeamRoleSoap[] toSoapModels(ContactTeamRole[] models) {
		ContactTeamRoleSoap[] soapModels =
			new ContactTeamRoleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ContactTeamRoleSoap[][] toSoapModels(
		ContactTeamRole[][] models) {

		ContactTeamRoleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ContactTeamRoleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ContactTeamRoleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ContactTeamRoleSoap[] toSoapModels(
		List<ContactTeamRole> models) {

		List<ContactTeamRoleSoap> soapModels =
			new ArrayList<ContactTeamRoleSoap>(models.size());

		for (ContactTeamRole model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ContactTeamRoleSoap[soapModels.size()]);
	}

	public ContactTeamRoleSoap() {
	}

	public ContactTeamRolePK getPrimaryKey() {
		return new ContactTeamRolePK(_contactId, _teamId, _contactRoleId);
	}

	public void setPrimaryKey(ContactTeamRolePK pk) {
		setContactId(pk.contactId);
		setTeamId(pk.teamId);
		setContactRoleId(pk.contactRoleId);
	}

	public long getContactId() {
		return _contactId;
	}

	public void setContactId(long contactId) {
		_contactId = contactId;
	}

	public long getTeamId() {
		return _teamId;
	}

	public void setTeamId(long teamId) {
		_teamId = teamId;
	}

	public long getContactRoleId() {
		return _contactRoleId;
	}

	public void setContactRoleId(long contactRoleId) {
		_contactRoleId = contactRoleId;
	}

	private long _contactId;
	private long _teamId;
	private long _contactRoleId;

}