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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceShipmentItemLocalServiceUtil;

/**
 * The extended model base implementation for the CommerceShipmentItem service. Represents a row in the &quot;CommerceShipmentItem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceShipmentItemImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemImpl
 * @see CommerceShipmentItem
 * @generated
 */
@ProviderType
public abstract class CommerceShipmentItemBaseImpl
	extends CommerceShipmentItemModelImpl implements CommerceShipmentItem {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce shipment item model instance should use the {@link CommerceShipmentItem} interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CommerceShipmentItemLocalServiceUtil.addCommerceShipmentItem(this);
		}
		else {
			CommerceShipmentItemLocalServiceUtil.updateCommerceShipmentItem(this);
		}
	}
}