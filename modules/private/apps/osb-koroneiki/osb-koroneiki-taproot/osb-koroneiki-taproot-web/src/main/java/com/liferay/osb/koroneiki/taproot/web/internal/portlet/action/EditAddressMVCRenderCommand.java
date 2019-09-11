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
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = {
		"javax.portlet.name=" + TaprootPortletKeys.ACCOUNTS_ADMIN,
		"mvc.command.name=/accounts_admin/edit_address"
	},
	service = MVCRenderCommand.class
)
public class EditAddressMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			long accountId = ParamUtil.getLong(renderRequest, "accountId");
			long addressId = ParamUtil.getLong(renderRequest, "addressId");

			if (accountId > 0) {
				renderRequest.setAttribute(
					TaprootWebKeys.ACCOUNT,
					_accountLocalService.getAccount(accountId));
			}

			if (addressId > 0) {
				renderRequest.setAttribute(
					WebKeys.ADDRESS,
					_addressLocalService.getAddress(addressId));
			}

			return "/accounts_admin/edit_address.jsp";
		}
		catch (Exception e) {
			SessionErrors.add(renderRequest, e.getClass());

			throw new PortletException(e);
		}
	}

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private AddressLocalService _addressLocalService;

}