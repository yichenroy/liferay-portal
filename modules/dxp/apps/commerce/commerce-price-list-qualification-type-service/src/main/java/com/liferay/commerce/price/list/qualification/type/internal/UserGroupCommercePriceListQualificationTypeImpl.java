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

package com.liferay.commerce.price.list.qualification.type.internal;

import com.liferay.commerce.price.CommercePriceListQualificationType;
import com.liferay.commerce.price.list.qualification.type.constants.CommercePriceListQualificationTypeConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.price.list.qualification.type.key=" + CommercePriceListQualificationTypeConstants.QUALIFICATION_TYPE_USER_GROUP,
		"commerce.price.list.qualification.type.priority:Integer=30"
	},
	service = CommercePriceListQualificationType.class
)
public class UserGroupCommercePriceListQualificationTypeImpl
	extends BaseCommercePriceListQualificationType {

	@Override
	public String getKey() {
		return CommercePriceListQualificationTypeConstants.
			QUALIFICATION_TYPE_USER_GROUP;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "user-groups");
	}

	@Override
	protected String getClassName() {
		return UserGroup.class.getName();
	}

	@Override
	protected long[] getUserClassPKs(User user) throws PortalException {
		return user.getUserGroupIds();
	}

}