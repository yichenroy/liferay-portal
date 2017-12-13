/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStepServicesTracker;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marco Leo
 */
public class CheckoutDisplayContext {

	public CheckoutDisplayContext(
			CommerceCartHelper commerceCartHelper,
			CommerceCheckoutStepServicesTracker
				commerceCheckoutStepServicesTracker,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, Portal portal)
		throws Exception {

		_commerceCartHelper = commerceCartHelper;
		_commerceCheckoutStepServicesTracker =
			commerceCheckoutStepServicesTracker;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = portal.getHttpServletRequest(
			_liferayPortletRequest);
		_httpServletResponse = portal.getHttpServletResponse(
			_liferayPortletResponse);

		_commerceCart = _commerceCartHelper.getCurrentCommerceCart(
			_httpServletRequest, _httpServletResponse,
			CommerceCartConstants.TYPE_CART);

		String checkoutStepName = ParamUtil.getString(
			liferayPortletRequest, "checkoutStepName");

		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.getCommerceCheckoutStep(
				checkoutStepName);

		if (commerceCheckoutStep == null) {
			List<CommerceCheckoutStep> commerceCheckoutSteps =
				_commerceCheckoutStepServicesTracker.getCommerceCheckoutSteps(
					_httpServletRequest, _httpServletResponse);

			commerceCheckoutStep = commerceCheckoutSteps.get(0);
		}

		_commerceCheckoutStep = commerceCheckoutStep;
	}

	public long getCommerceCartId() {
		if (_commerceCart == null) {
			return 0;
		}

		return _commerceCart.getCommerceCartId();
	}

	public List<CommerceCheckoutStep> getCommerceCheckoutSteps()
		throws Exception {

		return _commerceCheckoutStepServicesTracker.getCommerceCheckoutSteps(
			_httpServletRequest, _httpServletResponse);
	}

	public String getCurrentCheckoutStepName() {
		return _commerceCheckoutStep.getName();
	}

	public String getPreviousCheckoutStepName() throws Exception {
		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.
				getPreviousCommerceCheckoutStep(
					_commerceCheckoutStep.getName(), _httpServletRequest,
					_httpServletResponse);

		if (commerceCheckoutStep == null) {
			return null;
		}

		return commerceCheckoutStep.getName();
	}

	public String getRedirect() throws Exception {
		PortletURL portletURL = null;

		CommerceCheckoutStep commerceCheckoutStep =
			_commerceCheckoutStepServicesTracker.getNextCommerceCheckoutStep(
				_commerceCheckoutStep.getName(), _httpServletRequest,
				_httpServletResponse);

		if (commerceCheckoutStep == null) {
			portletURL = PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse);
		}
		else {
			portletURL = _liferayPortletResponse.createRenderURL();

			portletURL.setParameter(
				"checkoutStepName", commerceCheckoutStep.getName());
			portletURL.setParameter(
				"commerceCartId", String.valueOf(getCommerceCartId()));
		}

		return portletURL.toString();
	}

	public boolean isCurrentCommerceCheckoutStep(
		CommerceCheckoutStep commerceCheckoutStep) {

		if (getCurrentCheckoutStepName().equals(
				commerceCheckoutStep.getName())) {

			return true;
		}

		return false;
	}

	public boolean isSennaDisabled() {
		return _commerceCheckoutStep.isSennaDisabled();
	}

	public void renderCurrentCheckoutStep() throws Exception {
		_commerceCheckoutStep.render(_httpServletRequest, _httpServletResponse);
	}

	public boolean showControls() {
		return _commerceCheckoutStep.showControls(
			_httpServletRequest, _httpServletResponse);
	}

	private final CommerceCart _commerceCart;
	private final CommerceCartHelper _commerceCartHelper;
	private final CommerceCheckoutStep _commerceCheckoutStep;
	private final CommerceCheckoutStepServicesTracker
		_commerceCheckoutStepServicesTracker;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}