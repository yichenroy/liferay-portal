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
import com.liferay.osb.koroneiki.taproot.exception.ContactEmailAddressException;
import com.liferay.osb.koroneiki.taproot.exception.NoSuchContactException;
import com.liferay.osb.koroneiki.taproot.service.ContactService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	property = {
		"javax.portlet.name=" + TaprootPortletKeys.CONTACTS_ADMIN,
		"mvc.command.name=/contacts_admin/edit_contact"
	},
	service = MVCActionCommand.class
)
public class EditContactMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteContact(ActionRequest actionRequest)
		throws PortalException {

		long contactId = ParamUtil.getLong(actionRequest, "contactId");

		_contactService.deleteContact(contactId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteContact(actionRequest);
			}
			else {
				updateContact(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof ContactEmailAddressException ||
				e instanceof NoSuchContactException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/contacts_admin/edit_contact");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateContact(ActionRequest actionRequest)
		throws PortalException {

		long contactId = ParamUtil.getLong(actionRequest, "contactId");

		String uuid = ParamUtil.getString(actionRequest, "uuid");
		String oktaId = ParamUtil.getString(actionRequest, "oktaId");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		String languageId = ParamUtil.getString(actionRequest, "languageId");

		if (contactId <= 0) {
			_contactService.addContact(
				uuid, oktaId, firstName, middleName, lastName, emailAddress,
				languageId);
		}
		else {
			_contactService.updateContact(
				contactId, uuid, oktaId, firstName, middleName, lastName,
				emailAddress, languageId);
		}
	}

	@Reference
	private ContactService _contactService;

}