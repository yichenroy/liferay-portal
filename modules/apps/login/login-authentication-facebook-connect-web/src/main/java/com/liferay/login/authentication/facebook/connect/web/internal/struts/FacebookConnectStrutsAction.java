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

package com.liferay.login.authentication.facebook.connect.web.internal.struts;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectWebKeys;
import com.liferay.portal.security.sso.facebook.connect.exception.MustVerifyEmailAddressException;
import com.liferay.portal.security.sso.facebook.connect.exception.StrangersNotAllowedException;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Receives HTTP servlet requests much like a servlet.
 *
 * <p>
 * By setting the <code>path</code> property on the <code>@Component</code>
 * annotation to <code>/login/facebook_connect_oauth/</code>, this Struts action
 * is published at the URL <code>/c/login/facebook_connect_oauth/</code>, which
 * corresponds to the default OAuth redirect URL. This Struts action carries out
 * these tasks:
 * </p>
 *
 * <ol>
 * <li>
 * Exchanges OAuth request tokens for OAuth access tokens.
 * </li>
 * <li>
 * Retrieves the current user's Facebook ID and email address using the
 * Facebook Graph API.
 * </li>
 * <li>
 * If either the Facebook ID or email address matches an existing Liferay
 * user, then one of two HTTP session attributes is set:
 * <code>FACEBOOK_USER_ID</code> or <code>FACEBOOK_USER_EMAIL_ADDRESS</code>.
 * </li>
 * <li>
 * If no matching Liferay user is found, a new Liferay Portal user is
 * created and the HTTP session attribute
 * <code>FACEBOOK_USER_EMAIL_ADDRESS</code> is set accordingly. If the data
 * available from Facebook is insufficient to successfully create a new Liferay
 * user, the user is directed to submit the missing information to complete the
 * process.
 * </li>
 * </ol>
 *
 * @author     Wilson Man
 * @author     Sergio González
 * @author     Mika Koivisto
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Component(
	immediate = true,
	property = {
		"/common/referer_jsp.jsp=/common/referer_jsp.jsp",
		"path=/portal/facebook_connect_oauth"
	},
	service = StrutsAction.class
)
@Deprecated
public class FacebookConnectStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_facebookConnect.isEnabled(themeDisplay.getCompanyId())) {
			throw new PrincipalException.MustBeEnabled(
				themeDisplay.getCompanyId(), FacebookConnect.class.getName());
		}

		HttpSession session = httpServletRequest.getSession();

		String nonce = (String)session.getAttribute(WebKeys.FACEBOOK_NONCE);

		String state = ParamUtil.getString(httpServletRequest, "state");

		JSONObject stateJSONObject = JSONFactoryUtil.createJSONObject(state);

		String stateNonce = stateJSONObject.getString("stateNonce");

		if (!stateNonce.equals(nonce)) {
			throw new PrincipalException.MustHaveValidCSRFToken(
				_portal.getUserId(httpServletRequest),
				FacebookConnectStrutsAction.class.getName());
		}

		if (!Validator.isBlank(
				ParamUtil.getString(httpServletRequest, "error"))) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Authentication error: " +
						httpServletRequest.getQueryString());
			}

			return _forward;
		}

		String redirect = stateJSONObject.getString("redirect");

		redirect = _portal.escapeRedirect(redirect);

		String code = ParamUtil.getString(httpServletRequest, "code");

		String token = _facebookConnect.getAccessToken(
			themeDisplay.getCompanyId(), redirect, code);

		if (Validator.isNotNull(token)) {
			try {
				User user = setFacebookCredentials(
					session, themeDisplay.getCompanyId(), token);

				if ((user != null) &&
					(user.getStatus() == WorkflowConstants.STATUS_INCOMPLETE)) {

					redirectUpdateAccount(
						httpServletRequest, httpServletResponse, user);

					return null;
				}
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException, portalException);
				}

				Class<?> clazz = portalException.getClass();

				sendError(
					clazz.getSimpleName(), httpServletRequest,
					httpServletResponse);

				return null;
			}
		}
		else {
			return _forward;
		}

		httpServletResponse.sendRedirect(redirect);

		return null;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_forward = GetterUtil.getString(properties, "/common/referer_jsp.jsp");
	}

	protected User addUser(
			HttpSession session, long companyId, JSONObject jsonObject)
		throws Exception {

		long creatorUserId = 0;
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		String emailAddress = jsonObject.getString("email");
		long facebookId = jsonObject.getLong("id");
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = jsonObject.getString("first_name");
		String middleName = StringPool.BLANK;
		String lastName = jsonObject.getString("last_name");
		long prefixId = 0;
		long suffixId = 0;
		boolean male = Objects.equals(jsonObject.getString("gender"), "male");
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = true;

		ServiceContext serviceContext = new ServiceContext();

		User user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		user = _userLocalService.updateLastLogin(
			user.getUserId(), user.getLoginIP());

		user = _userLocalService.updatePasswordReset(user.getUserId(), false);

		user = _userLocalService.updateEmailAddressVerified(
			user.getUserId(), true);

		session.setAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);

		return user;
	}

	protected void redirectUpdateAccount(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, User user)
		throws Exception {

		PortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, PortletKeys.LOGIN, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		portletURL.setParameter(
			"mvcRenderCommandName", "/login/associate_facebook_user");
		portletURL.setParameter(
			"redirect", ParamUtil.getString(httpServletRequest, "redirect"));
		portletURL.setParameter("userId", String.valueOf(user.getUserId()));
		portletURL.setParameter("emailAddress", user.getEmailAddress());
		portletURL.setParameter("firstName", user.getFirstName());
		portletURL.setParameter("lastName", user.getLastName());
		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		httpServletResponse.sendRedirect(portletURL.toString());
	}

	protected void sendError(
			String error, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			httpServletRequest, PortletKeys.LOGIN, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/login/facebook_connect_login_error");
		portletURL.setParameter("error", error);
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		httpServletResponse.sendRedirect(portletURL.toString());
	}

	@Reference(unbind = "-")
	protected void setFacebookConnect(FacebookConnect facebookConnect) {
		_facebookConnect = facebookConnect;
	}

	protected User setFacebookCredentials(
			HttpSession session, long companyId, String token)
		throws Exception {

		JSONObject jsonObject = _facebookConnect.getGraphResources(
			companyId, "/me", token,
			"id,email,first_name,last_name,gender,verified");

		if ((jsonObject == null) ||
			(jsonObject.getJSONObject("error") != null)) {

			return null;
		}

		if (_facebookConnect.isVerifiedAccountRequired(companyId) &&
			!jsonObject.getBoolean("verified")) {

			throw new MustVerifyEmailAddressException(companyId);
		}

		User user = null;

		long facebookId = jsonObject.getLong("id");

		if (facebookId > 0) {
			session.setAttribute(
				FacebookConnectWebKeys.FACEBOOK_ACCESS_TOKEN, token);

			user = _userLocalService.fetchUserByFacebookId(
				companyId, facebookId);

			if ((user != null) && !user.isActive()) {
				return null;
			}
			else if ((user != null) &&
					 (user.getStatus() !=
						 WorkflowConstants.STATUS_INCOMPLETE)) {

				session.setAttribute(
					FacebookConnectWebKeys.FACEBOOK_USER_ID,
					String.valueOf(facebookId));
			}
		}

		String emailAddress = jsonObject.getString("email");

		if ((user == null) && Validator.isNotNull(emailAddress)) {
			user = _userLocalService.fetchUserByEmailAddress(
				companyId, emailAddress);

			if ((user != null) && !user.isActive()) {
				return null;
			}
			else if ((user != null) &&
					 (user.getStatus() !=
						 WorkflowConstants.STATUS_INCOMPLETE)) {

				session.setAttribute(
					WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);
			}
		}

		if (user != null) {
			if (user.getStatus() == WorkflowConstants.STATUS_INCOMPLETE) {
				session.setAttribute(
					WebKeys.FACEBOOK_INCOMPLETE_USER_ID, facebookId);

				user.setEmailAddress(jsonObject.getString("email"));
				user.setFirstName(jsonObject.getString("first_name"));
				user.setLastName(jsonObject.getString("last_name"));

				return user;
			}

			user = updateUser(user, jsonObject);
		}
		else {
			_checkAllowUserCreation(companyId, jsonObject);

			user = addUser(session, companyId, jsonObject);
		}

		return user;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected User updateUser(User user, JSONObject jsonObject)
		throws Exception {

		long facebookId = jsonObject.getLong("id");
		String emailAddress = jsonObject.getString("email");
		String firstName = jsonObject.getString("first_name");
		String lastName = jsonObject.getString("last_name");
		boolean male = Objects.equals(jsonObject.getString("gender"), "male");

		if ((facebookId == user.getFacebookId()) &&
			emailAddress.equals(user.getEmailAddress()) &&
			firstName.equals(user.getFirstName()) &&
			lastName.equals(user.getLastName()) && (male == user.isMale())) {

			return user;
		}

		Contact contact = user.getContact();

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(contact.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;

		ServiceContext serviceContext = new ServiceContext();

		if (!StringUtil.equalsIgnoreCase(
				emailAddress, user.getEmailAddress())) {

			_userLocalService.updateEmailAddress(
				user.getUserId(), StringPool.BLANK, emailAddress, emailAddress);
		}

		_userLocalService.updateEmailAddressVerified(user.getUserId(), true);

		return _userLocalService.updateUser(
			user.getUserId(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, false, user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(), user.getScreenName(), emailAddress,
			facebookId, user.getOpenId(), true, null, user.getLanguageId(),
			user.getTimeZoneId(), user.getGreeting(), user.getComments(),
			firstName, user.getMiddleName(), lastName, contact.getPrefixId(),
			contact.getSuffixId(), male, birthdayMonth, birthdayDay,
			birthdayYear, contact.getSmsSn(), contact.getFacebookSn(),
			contact.getJabberSn(), contact.getSkypeSn(), contact.getTwitterSn(),
			contact.getJobTitle(), groupIds, organizationIds, roleIds,
			userGroupRoles, userGroupIds, serviceContext);
	}

	private void _checkAllowUserCreation(long companyId, JSONObject jsonObject)
		throws Exception {

		Company company = _companyLocalService.getCompany(companyId);

		if (!company.isStrangers()) {
			throw new StrangersNotAllowedException(companyId);
		}

		String emailAddress = jsonObject.getString("email");

		if (company.hasCompanyMx(emailAddress) &&
			!company.isStrangersWithMx()) {

			throw new UserEmailAddressException.MustNotUseCompanyMx(
				emailAddress);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacebookConnectStrutsAction.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	private FacebookConnect _facebookConnect;
	private String _forward;

	@Reference
	private Portal _portal;

	private UserLocalService _userLocalService;

}