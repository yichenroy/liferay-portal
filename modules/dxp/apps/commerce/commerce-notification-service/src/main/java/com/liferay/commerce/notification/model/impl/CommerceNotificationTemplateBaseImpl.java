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

package com.liferay.commerce.notification.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalServiceUtil;

/**
 * The extended model base implementation for the CommerceNotificationTemplate service. Represents a row in the &quot;CommerceNotificationTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceNotificationTemplateImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateImpl
 * @see CommerceNotificationTemplate
 * @generated
 */
@ProviderType
public abstract class CommerceNotificationTemplateBaseImpl
	extends CommerceNotificationTemplateModelImpl
	implements CommerceNotificationTemplate {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce notification template model instance should use the {@link CommerceNotificationTemplate} interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CommerceNotificationTemplateLocalServiceUtil.addCommerceNotificationTemplate(this);
		}
		else {
			CommerceNotificationTemplateLocalServiceUtil.updateCommerceNotificationTemplate(this);
		}
	}
}