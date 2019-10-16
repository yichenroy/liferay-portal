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

package com.liferay.commerce.user.segment.web.internal.servlet.taglib.ui;

import com.liferay.commerce.user.segment.constants.CommerceUserSegmentScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "screen.navigation.category.order:Integer=10",
	service = ScreenNavigationCategory.class
)
public class CommerceUserSegmentEntryDetailScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceUserSegmentScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_USER_SEGMENT_DETAIL;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale,
			CommerceUserSegmentScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_USER_SEGMENT_DETAIL);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceUserSegmentScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_USER_SEGMENT_ENTRY_GENERAL;
	}

}