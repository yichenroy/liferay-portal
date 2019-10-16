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

package com.liferay.portal.reports.engine.console.web.admin.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;

import javax.portlet.PortletRequest;

/**
 * @author Rafael Praxedes
 */
public class EntryDisplayTerms extends DisplayTerms {

	public static final String DEFINITION_NAME = "definitionName";

	public static final String END_DATE_DAY = "endDateDay";

	public static final String END_DATE_MONTH = "endDateMonth";

	public static final String END_DATE_YEAR = "endDateYear";

	public static final String START_DATE_DAY = "startDateDay";

	public static final String START_DATE_MONTH = "startDateMonth";

	public static final String START_DATE_YEAR = "startDateYear";

	public static final String USERNAME = "userName";

	public EntryDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		definitionName = ParamUtil.getString(portletRequest, DEFINITION_NAME);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			themeDisplay.getTimeZone(), themeDisplay.getLocale());

		endDateDay = ParamUtil.getInteger(
			portletRequest, END_DATE_DAY, calendar.get(Calendar.DATE));
		endDateMonth = ParamUtil.getInteger(
			portletRequest, END_DATE_MONTH, calendar.get(Calendar.MONTH));
		endDateYear = ParamUtil.getInteger(
			portletRequest, END_DATE_YEAR, calendar.get(Calendar.YEAR));

		calendar.add(Calendar.DATE, -1);

		startDateDay = ParamUtil.getInteger(
			portletRequest, START_DATE_DAY, calendar.get(Calendar.DATE));
		startDateMonth = ParamUtil.getInteger(
			portletRequest, START_DATE_MONTH, calendar.get(Calendar.MONTH));
		startDateYear = ParamUtil.getInteger(
			portletRequest, START_DATE_YEAR, calendar.get(Calendar.YEAR));

		userName = ParamUtil.getString(portletRequest, USERNAME);
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public int getEndDateDay() {
		return endDateDay;
	}

	public int getEndDateMonth() {
		return endDateMonth;
	}

	public int getEndDateYear() {
		return endDateYear;
	}

	public int getStartDateDay() {
		return startDateDay;
	}

	public int getStartDateMonth() {
		return startDateMonth;
	}

	public int getStartDateYear() {
		return startDateYear;
	}

	public String getUserName() {
		return userName;
	}

	protected String definitionName;
	protected int endDateDay;
	protected int endDateMonth;
	protected int endDateYear;
	protected int startDateDay;
	protected int startDateMonth;
	protected int startDateYear;
	protected String userName;

}