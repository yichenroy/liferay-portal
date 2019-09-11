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
import com.liferay.osb.koroneiki.taproot.exception.AccountNameException;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.service.AccountService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

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
	service = MVCActionCommand.class
)
public class EditAccountMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteAccount(ActionRequest actionRequest)
		throws PortalException {

		long accountId = ParamUtil.getLong(actionRequest, "accountId");

		_accountService.deleteAccount(accountId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteAccount(actionRequest);
			}
			else {
				updateAccount(actionRequest, actionResponse);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof AccountNameException) {
				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/accounts_admin/edit_account");
			}
			else {
				_log.error(e, e);

				throw e;
			}
		}
	}

	protected void updateAccount(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		long accountId = ParamUtil.getLong(actionRequest, "accountId");

		long parentAccountId = ParamUtil.getLong(
			actionRequest, "parentAccountId");
		String name = ParamUtil.getString(actionRequest, "name");
		String code = ParamUtil.getString(actionRequest, "code");
		String description = ParamUtil.getString(actionRequest, "description");
		String notes = ParamUtil.getString(actionRequest, "notes");
		String contactEmailAddress = ParamUtil.getString(
			actionRequest, "contactEmailAddress");
		String profileEmailAddress = ParamUtil.getString(
			actionRequest, "profileEmailAddress");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");
		String faxNumber = ParamUtil.getString(actionRequest, "faxNumber");
		String website = ParamUtil.getString(actionRequest, "website");
		String industry = ParamUtil.getString(actionRequest, "industry");
		String tier = ParamUtil.getString(actionRequest, "tier");
		String soldBy = ParamUtil.getString(actionRequest, "soldBy");
		int status = ParamUtil.getInteger(actionRequest, "status");

		Account account = null;

		if (accountId <= 0) {
			account = _accountService.addAccount(
				parentAccountId, name, code, description, notes, 0,
				contactEmailAddress, profileEmailAddress, phoneNumber,
				faxNumber, website, industry, tier, soldBy, status);
		}
		else {
			account = _accountService.updateAccount(
				accountId, parentAccountId, name, code, description, notes, 0,
				contactEmailAddress, profileEmailAddress, phoneNumber,
				faxNumber, website, industry, tier, soldBy, status);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL renderURL = liferayPortletResponse.createRenderURL();

		renderURL.setParameter(
			"mvcRenderCommandName", "/accounts_admin/edit_account");
		renderURL.setParameter("redirect", redirect);
		renderURL.setParameter(
			"accountId", String.valueOf(account.getAccountId()));

		actionRequest.setAttribute(WebKeys.REDIRECT, renderURL.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditAccountMVCActionCommand.class);

	@Reference
	private AccountService _accountService;

	@Reference
	private Portal _portal;

}