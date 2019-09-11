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

package com.liferay.osb.koroneiki.trunk.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ProductConsumption service. Represents a row in the &quot;Koroneiki_ProductConsumption&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ProductConsumptionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.osb.koroneiki.trunk.model.impl.ProductConsumptionImpl"
)
@ProviderType
public interface ProductConsumption
	extends PersistedModel, ProductConsumptionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductConsumptionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ProductConsumption, Long>
		PRODUCT_CONSUMPTION_ID_ACCESSOR =
			new Accessor<ProductConsumption, Long>() {

				@Override
				public Long get(ProductConsumption productConsumption) {
					return productConsumption.getProductConsumptionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ProductConsumption> getTypeClass() {
					return ProductConsumption.class;
				}

			};

	public com.liferay.osb.koroneiki.taproot.model.Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getAccountKey()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.osb.koroneiki.root.model.ExternalLink>
		getExternalLinks();

	public ProductEntry getProductEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getProductEntryKey()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<ProductField> getProductFields();

	public java.util.Map<String, String> getProductFieldsMap();

}