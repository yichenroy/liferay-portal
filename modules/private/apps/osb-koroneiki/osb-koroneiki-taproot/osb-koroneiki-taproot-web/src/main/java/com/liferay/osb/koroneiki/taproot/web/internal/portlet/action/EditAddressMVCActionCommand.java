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
import com.liferay.portal.kernel.exception.AddressCityException;
import com.liferay.portal.kernel.exception.AddressStreetException;
import com.liferay.portal.kernel.exception.AddressZipException;
import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

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
	service = MVCActionCommand.class
)
public class EditAddressMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteAddress(ActionRequest actionRequest)
		throws PortalException {

		long addressId = ParamUtil.getLong(actionRequest, "addressId");

		_addressLocalService.deleteAddress(addressId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteAddress(actionRequest);
			}
			else {
				updateAddress(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof AddressCityException ||
				e instanceof AddressStreetException ||
				e instanceof AddressZipException ||
				e instanceof NoSuchCountryException ||
				e instanceof NoSuchListTypeException ||
				e instanceof NoSuchRegionException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/accounts_admin/edit_address");
			}
			else {
				_log.error(e, e);

				throw e;
			}
		}
	}

	protected void updateAddress(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long addressId = ParamUtil.getLong(actionRequest, "addressId");

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String street1 = ParamUtil.getString(actionRequest, "addressStreet1");
		String street2 = ParamUtil.getString(actionRequest, "addressStreet2");
		String street3 = ParamUtil.getString(actionRequest, "addressStreet3");
		String city = ParamUtil.getString(actionRequest, "addressCity");
		String zip = ParamUtil.getString(actionRequest, "addressZip");
		int regionId = ParamUtil.getInteger(actionRequest, "addressRegionId");
		int countryId = ParamUtil.getInteger(actionRequest, "addressCountryId");
		int typeId = ParamUtil.getInteger(actionRequest, "addressTypeId");
		boolean mailing = ParamUtil.getBoolean(actionRequest, "addressMailing");
		boolean primary = ParamUtil.getBoolean(actionRequest, "addressPrimary");

		if (addressId <= 0) {
			_addressLocalService.addAddress(
				themeDisplay.getUserId(), className, classPK, street1, street2,
				street3, city, zip, regionId, countryId, typeId, mailing,
				primary, new ServiceContext());
		}
		else {
			_addressLocalService.updateAddress(
				addressId, street1, street2, street3, city, zip, regionId,
				countryId, typeId, mailing, primary);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditAddressMVCActionCommand.class);

	@Reference
	private AddressLocalService _addressLocalService;

}