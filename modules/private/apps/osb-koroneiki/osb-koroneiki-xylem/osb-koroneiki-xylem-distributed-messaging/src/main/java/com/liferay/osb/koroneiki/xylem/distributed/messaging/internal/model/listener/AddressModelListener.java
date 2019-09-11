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

package com.liferay.osb.koroneiki.xylem.distributed.messaging.internal.model.listener;

import com.liferay.osb.distributed.messaging.Message;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = ModelListener.class)
public class AddressModelListener extends BaseXylemModelListener<Address> {

	@Override
	public Message createMessage(Address address) throws Exception {
		if (address.getClassNameId() == _classNameLocalService.getClassNameId(
				Account.class)) {

			Account account = _accountLocalService.getAccount(
				address.getClassPK());

			return messageFactory.create(account);
		}

		return null;
	}

	@Override
	protected String getCreateTopic(Address address) {
		return _getTopic(address);
	}

	@Override
	protected String getRemoveTopic(Address address) {
		return _getTopic(address);
	}

	@Override
	protected String getUpdateTopic(Address address) {
		return _getTopic(address);
	}

	private String _getTopic(Address address) {
		if (address.getClassNameId() == _classNameLocalService.getClassNameId(
				Account.class)) {

			return "koroneiki.account.update";
		}

		return null;
	}

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}