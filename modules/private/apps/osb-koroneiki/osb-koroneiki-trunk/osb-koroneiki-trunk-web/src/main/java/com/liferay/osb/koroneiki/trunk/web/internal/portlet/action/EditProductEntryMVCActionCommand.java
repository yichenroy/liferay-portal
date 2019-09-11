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

package com.liferay.osb.koroneiki.trunk.web.internal.portlet.action;

import com.liferay.osb.koroneiki.trunk.constants.TrunkPortletKeys;
import com.liferay.osb.koroneiki.trunk.exception.ProductEntryNameException;
import com.liferay.osb.koroneiki.trunk.service.ProductEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
		"javax.portlet.name=" + TrunkPortletKeys.PRODUCTS_ADMIN,
		"mvc.command.name=/products_admin/edit_product_entry"
	},
	service = MVCActionCommand.class
)
public class EditProductEntryMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteProductEntry(ActionRequest actionRequest)
		throws PortalException {

		long productEntryId = ParamUtil.getLong(
			actionRequest, "productEntryId");

		_productEntryService.deleteProductEntry(productEntryId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteProductEntry(actionRequest);
			}
			else {
				updateProductEntry(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof ProductEntryNameException) {
				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/products_admin/edit_product_entry");
			}
			else {
				_log.error(e, e);

				throw e;
			}
		}
	}

	protected void updateProductEntry(ActionRequest actionRequest)
		throws PortalException {

		long productEntryId = ParamUtil.getLong(
			actionRequest, "productEntryId");

		String name = ParamUtil.getString(actionRequest, "name");

		if (productEntryId <= 0) {
			_productEntryService.addProductEntry(name);
		}
		else {
			_productEntryService.updateProductEntry(productEntryId, name);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditProductEntryMVCActionCommand.class);

	@Reference
	private ProductEntryService _productEntryService;

}