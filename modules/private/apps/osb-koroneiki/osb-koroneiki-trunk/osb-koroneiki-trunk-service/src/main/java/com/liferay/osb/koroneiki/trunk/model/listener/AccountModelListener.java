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

package com.liferay.osb.koroneiki.trunk.model.listener;

import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.trunk.model.ProductConsumption;
import com.liferay.osb.koroneiki.trunk.model.ProductPurchase;
import com.liferay.osb.koroneiki.trunk.service.ProductConsumptionLocalService;
import com.liferay.osb.koroneiki.trunk.service.ProductPurchaseLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = ModelListener.class)
public class AccountModelListener extends BaseModelListener<Account> {

	@Override
	public void onAfterRemove(Account account) throws ModelListenerException {
		try {

			// Product consumptions

			List<ProductConsumption> productConsumptions =
				_productConsumptionLocalService.getAccountProductConsumptions(
					account.getAccountId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			for (ProductConsumption productConsumption : productConsumptions) {
				_productConsumptionLocalService.deleteProductConsumption(
					productConsumption.getProductConsumptionId());
			}

			// Product purchases

			List<ProductPurchase> productPurchases =
				_productPurchaseLocalService.getAccountProductPurchases(
					account.getAccountId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			for (ProductPurchase productPurchase : productPurchases) {
				_productPurchaseLocalService.deleteProductPurchase(
					productPurchase.getProductPurchaseId());
			}
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Reference
	private ProductConsumptionLocalService _productConsumptionLocalService;

	@Reference
	private ProductPurchaseLocalService _productPurchaseLocalService;

}