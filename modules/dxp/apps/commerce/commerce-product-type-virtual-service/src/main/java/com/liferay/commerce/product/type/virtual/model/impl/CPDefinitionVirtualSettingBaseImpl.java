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

package com.liferay.commerce.product.type.virtual.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalServiceUtil;

/**
 * The extended model base implementation for the CPDefinitionVirtualSetting service. Represents a row in the &quot;CPDefinitionVirtualSetting&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CPDefinitionVirtualSettingImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingImpl
 * @see CPDefinitionVirtualSetting
 * @generated
 */
@ProviderType
public abstract class CPDefinitionVirtualSettingBaseImpl
	extends CPDefinitionVirtualSettingModelImpl
	implements CPDefinitionVirtualSetting {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cp definition virtual setting model instance should use the {@link CPDefinitionVirtualSetting} interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CPDefinitionVirtualSettingLocalServiceUtil.addCPDefinitionVirtualSetting(this);
		}
		else {
			CPDefinitionVirtualSettingLocalServiceUtil.updateCPDefinitionVirtualSetting(this);
		}
	}
}