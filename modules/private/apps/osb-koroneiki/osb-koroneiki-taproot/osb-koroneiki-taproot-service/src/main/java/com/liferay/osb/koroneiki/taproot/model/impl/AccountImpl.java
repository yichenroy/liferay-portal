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

package com.liferay.osb.koroneiki.taproot.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osb.koroneiki.root.model.ExternalLink;
import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalServiceUtil;
import com.liferay.osb.koroneiki.taproot.constants.WorkflowConstants;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;

import java.util.List;

/**
 * @author Kyle Bischof
 */
@ProviderType
public class AccountImpl extends AccountBaseImpl {

	public AccountImpl() {
	}

	public List<Address> getAddresses() {
		return AddressLocalServiceUtil.getAddresses(
			getCompanyId(), Account.class.getName(), getAccountId());
	}

	public List<Account> getChildAccounts() throws PortalException {
		return AccountLocalServiceUtil.getAccounts(
			getAccountId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<ExternalLink> getExternalLinks() {
		return ExternalLinkLocalServiceUtil.getExternalLinks(
			Account.class.getName(), getAccountId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public Account getParentAccount() throws PortalException {
		if (getParentAccountId() <= 0) {
			return null;
		}

		return AccountLocalServiceUtil.getAccount(getParentAccountId());
	}

	public String getStatusLabel() {
		return WorkflowConstants.getStatusLabel(getStatus());
	}

}