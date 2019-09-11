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

package com.liferay.osb.koroneiki.root.audit.model.listener;

import com.liferay.osb.koroneiki.root.audit.model.BaseAuditModelListener;
import com.liferay.osb.koroneiki.trunk.model.ProductField;
import com.liferay.osb.koroneiki.trunk.model.ProductPurchase;
import com.liferay.osb.koroneiki.trunk.service.ProductFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(immediate = true, service = ModelListener.class)
public class ProductFieldModelListener
	extends BaseAuditModelListener<ProductField> {

	@Override
	protected long getClassNameId(ProductField productField) {
		return classNameLocalService.getClassNameId(ProductPurchase.class);
	}

	@Override
	protected long getClassPK(ProductField productField) {
		return productField.getClassPK();
	}

	@Override
	protected ProductField getModel(long classPK) throws PortalException {
		return _productFieldLocalService.getProductField(classPK);
	}

	@Reference
	private ProductFieldLocalService _productFieldLocalService;

}