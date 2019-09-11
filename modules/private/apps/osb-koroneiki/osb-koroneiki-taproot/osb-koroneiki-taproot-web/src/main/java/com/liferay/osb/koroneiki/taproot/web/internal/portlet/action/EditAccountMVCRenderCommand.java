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

package com.liferay.osb.koroneiki.taproot.web.internal.portlet.action;

import com.liferay.osb.koroneiki.taproot.constants.TaprootPortletKeys;
import com.liferay.osb.koroneiki.taproot.constants.TaprootWebKeys;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	property = {
		"javax.portlet.name=" + TaprootPortletKeys.ACCOUNTS_ADMIN,
		"mvc.command.name=/accounts_admin/edit_account"
	},
	service = MVCRenderCommand.class
)
public class EditAccountMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			long accountId = ParamUtil.getLong(renderRequest, "accountId");

			if (accountId > 0) {
				renderRequest.setAttribute(
					TaprootWebKeys.ACCOUNT,
					_accountLocalService.getAccount(accountId));
			}

			String tabs1 = ParamUtil.getString(renderRequest, "tabs1");

			if (tabs1.equals("addresses")) {
				return "/accounts_admin/edit_account_addresses.jsp";
			}
			else if (tabs1.equals("assigned-teams")) {
				return "/accounts_admin/edit_account_assigned_teams.jsp";
			}
			else if (tabs1.equals("child-accounts")) {
				return "/accounts_admin/edit_account_child_accounts.jsp";
			}
			else if (tabs1.equals("contact-roles")) {
				return "/accounts_admin/edit_account_contact_roles.jsp";
			}
			else if (tabs1.equals("external-links")) {
				return "/accounts_admin/edit_account_external_links.jsp";
			}
			else if (tabs1.equals("teams")) {
				return "/accounts_admin/edit_account_teams.jsp";
			}
			else {
				return "/accounts_admin/edit_account.jsp";
			}
		}
		catch (Exception e) {
			SessionErrors.add(renderRequest, e.getClass());

			return "/accounts_admin/error.jsp";
		}
	}

	@Reference
	private AccountLocalService _accountLocalService;

}