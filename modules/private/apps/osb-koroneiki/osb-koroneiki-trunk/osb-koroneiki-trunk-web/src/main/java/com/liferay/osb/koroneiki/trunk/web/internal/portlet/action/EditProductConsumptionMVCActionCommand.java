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

import com.liferay.osb.koroneiki.taproot.exception.NoSuchAccountException;
import com.liferay.osb.koroneiki.trunk.constants.TrunkPortletKeys;
import com.liferay.osb.koroneiki.trunk.exception.NoSuchProductEntryException;
import com.liferay.osb.koroneiki.trunk.model.ProductField;
import com.liferay.osb.koroneiki.trunk.service.ProductConsumptionService;
import com.liferay.osb.koroneiki.trunk.service.ProductFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

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
		"mvc.command.name=/products_admin/edit_product_consumption"
	},
	service = MVCActionCommand.class
)
public class EditProductConsumptionMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addProductConsumption(ActionRequest actionRequest)
		throws PortalException {

		long accountId = ParamUtil.getLong(actionRequest, "accountId");
		long productEntryId = ParamUtil.getLong(
			actionRequest, "productEntryId");

		List<ProductField> productFields = new ArrayList<>();

		int[] productFieldIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "productFieldIndexes"), 0);

		for (int productFieldIndex : productFieldIndexes) {
			String productFieldName = ParamUtil.getString(
				actionRequest, "productFieldName_" + productFieldIndex);
			String productFieldValue = ParamUtil.getString(
				actionRequest, "productFieldValue_" + productFieldIndex);

			if (Validator.isNull(productFieldName) ||
				Validator.isNull(productFieldValue)) {

				continue;
			}

			ProductField productField =
				_productFieldLocalService.createProductField(0);

			productField.setName(productFieldName);
			productField.setValue(productFieldValue);

			productFields.add(productField);
		}

		_productConsumptionService.addProductConsumption(
			accountId, productEntryId, productFields);
	}

	protected void deleteProductConsumption(ActionRequest actionRequest)
		throws PortalException {

		long productConsumptionId = ParamUtil.getLong(
			actionRequest, "productConsumptionId");

		_productConsumptionService.deleteProductConsumption(
			productConsumptionId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteProductConsumption(actionRequest);
			}
			else {
				addProductConsumption(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchAccountException ||
				e instanceof NoSuchProductEntryException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/products_admin/edit_product_consumption");
			}
			else {
				_log.error(e, e);

				throw e;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditProductConsumptionMVCActionCommand.class);

	@Reference
	private ProductConsumptionService _productConsumptionService;

	@Reference
	private ProductFieldLocalService _productFieldLocalService;

}