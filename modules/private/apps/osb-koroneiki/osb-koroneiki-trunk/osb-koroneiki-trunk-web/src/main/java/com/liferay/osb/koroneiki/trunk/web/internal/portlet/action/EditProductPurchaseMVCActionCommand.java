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
import com.liferay.osb.koroneiki.trunk.exception.ProductPurchaseEndDateException;
import com.liferay.osb.koroneiki.trunk.exception.ProductPurchaseQuantityException;
import com.liferay.osb.koroneiki.trunk.model.ProductField;
import com.liferay.osb.koroneiki.trunk.service.ProductFieldLocalService;
import com.liferay.osb.koroneiki.trunk.service.ProductPurchaseService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Date;
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
		"mvc.command.name=/products_admin/edit_product_purchase"
	},
	service = MVCActionCommand.class
)
public class EditProductPurchaseMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteProductPurchase(ActionRequest actionRequest)
		throws PortalException {

		long productPurchaseId = ParamUtil.getLong(
			actionRequest, "productPurchaseId");

		_productPurchaseService.deleteProductPurchase(productPurchaseId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteProductPurchase(actionRequest);
			}
			else {
				updateProductPurchase(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchAccountException ||
				e instanceof NoSuchProductEntryException ||
				e instanceof ProductPurchaseEndDateException ||
				e instanceof ProductPurchaseQuantityException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/products_admin/edit_product_purchase");
			}
			else {
				_log.error(e, e);

				throw e;
			}
		}
	}

	protected void updateProductPurchase(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long productPurchaseId = ParamUtil.getLong(
			actionRequest, "productPurchaseId");

		long accountId = ParamUtil.getLong(actionRequest, "accountId");
		long productEntryId = ParamUtil.getLong(
			actionRequest, "productEntryId");

		int startDateMonth = ParamUtil.getInteger(
			actionRequest, "startDateMonth");
		int startDateDay = ParamUtil.getInteger(actionRequest, "startDateDay");
		int startDateYear = ParamUtil.getInteger(
			actionRequest, "startDateYear");

		Date startDate = _portal.getDate(
			startDateMonth, startDateDay, startDateYear,
			themeDisplay.getTimeZone(), null);

		int endDateMonth = ParamUtil.getInteger(actionRequest, "endDateMonth");
		int endDateDay = ParamUtil.getInteger(actionRequest, "endDateDay");
		int endDateYear = ParamUtil.getInteger(actionRequest, "endDateYear");

		Date endDate = _portal.getDate(
			endDateMonth, endDateDay, endDateYear, themeDisplay.getTimeZone(),
			null);

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

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

		if (productPurchaseId <= 0) {
			_productPurchaseService.addProductPurchase(
				accountId, productEntryId, startDate, endDate, quantity,
				productFields);
		}
		else {
			_productPurchaseService.updateProductPurchase(
				productPurchaseId, startDate, endDate, quantity, productFields);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditProductPurchaseMVCActionCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private ProductFieldLocalService _productFieldLocalService;

	@Reference
	private ProductPurchaseService _productPurchaseService;

}