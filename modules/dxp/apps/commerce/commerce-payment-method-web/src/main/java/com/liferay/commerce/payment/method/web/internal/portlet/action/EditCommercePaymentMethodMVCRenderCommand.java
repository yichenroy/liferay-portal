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

package com.liferay.commerce.payment.method.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.exception.NoSuchPaymentMethodException;
import com.liferay.commerce.payment.method.web.internal.display.context.CommercePaymentMethodsDisplayContext;
import com.liferay.commerce.service.CommercePaymentMethodService;
import com.liferay.commerce.util.CommercePaymentEngineRegistry;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = {
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommercePaymentMethod"
	},
	service = MVCRenderCommand.class
)
public class EditCommercePaymentMethodMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/edit_payment_method.jsp");

		try {
			CommercePaymentMethodsDisplayContext
				commercePaymentMethodsDisplayContext =
					new CommercePaymentMethodsDisplayContext(
						_commercePaymentEngineRegistry,
						_commercePaymentMethodService, renderRequest,
						renderResponse);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commercePaymentMethodsDisplayContext);

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchPaymentMethodException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/error.jsp";
			}
			else {
				throw new PortletException(
					"Unable to include edit_payment_method.jsp", e);
			}
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	@Reference
	private CommercePaymentEngineRegistry _commercePaymentEngineRegistry;

	@Reference
	private CommercePaymentMethodService _commercePaymentMethodService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.payment.method.web)"
	)
	private ServletContext _servletContext;

}