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
import com.liferay.osb.koroneiki.taproot.exception.ContactRoleNameException;
import com.liferay.osb.koroneiki.taproot.exception.ContactRoleTypeException;
import com.liferay.osb.koroneiki.taproot.exception.NoSuchContactRoleException;
import com.liferay.osb.koroneiki.taproot.service.ContactRoleService;
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
		"javax.portlet.name=" + TaprootPortletKeys.CONTACT_ROLES_ADMIN,
		"mvc.command.name=/contact_roles_admin/edit_contact_role"
	},
	service = MVCActionCommand.class
)
public class EditContactRoleMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteContactRole(ActionRequest actionRequest)
		throws PortalException {

		long contactRoleId = ParamUtil.getLong(actionRequest, "contactRoleId");

		_contactRoleService.deleteContactRole(contactRoleId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteContactRole(actionRequest);
			}
			else {
				updateContactRole(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof ContactRoleNameException ||
				e instanceof ContactRoleTypeException ||
				e instanceof NoSuchContactRoleException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/contact_roles_admin/edit_contact_role");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateContactRole(ActionRequest actionRequest)
		throws PortalException {

		long contactRoleId = ParamUtil.getLong(actionRequest, "contactRoleId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		int type = ParamUtil.getInteger(actionRequest, "type");

		if (contactRoleId <= 0) {
			_contactRoleService.addContactRole(name, description, type);
		}
		else {
			_contactRoleService.updateContactRole(
				contactRoleId, name, description);
		}
	}

	@Reference
	private ContactRoleService _contactRoleService;

}