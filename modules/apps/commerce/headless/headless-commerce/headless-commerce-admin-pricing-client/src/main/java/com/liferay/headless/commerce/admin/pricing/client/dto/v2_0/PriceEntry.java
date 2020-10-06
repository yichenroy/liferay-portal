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

package com.liferay.headless.commerce.admin.pricing.client.dto.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceEntrySerDes;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class PriceEntry implements Cloneable {

	public static PriceEntry toDTO(String json) {
		return PriceEntrySerDes.toDTO(json);
	}

	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Map<String, String>> actions;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean active;

	public Boolean getBulkPricing() {
		return bulkPricing;
	}

	public void setBulkPricing(Boolean bulkPricing) {
		this.bulkPricing = bulkPricing;
	}

	public void setBulkPricing(
		UnsafeSupplier<Boolean, Exception> bulkPricingUnsafeSupplier) {

		try {
			bulkPricing = bulkPricingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean bulkPricing;

	public Map<String, ?> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Map<String, ?> customFields) {
		this.customFields = customFields;
	}

	public void setCustomFields(
		UnsafeSupplier<Map<String, ?>, Exception> customFieldsUnsafeSupplier) {

		try {
			customFields = customFieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, ?> customFields;

	public Boolean getDiscountDiscovery() {
		return discountDiscovery;
	}

	public void setDiscountDiscovery(Boolean discountDiscovery) {
		this.discountDiscovery = discountDiscovery;
	}

	public void setDiscountDiscovery(
		UnsafeSupplier<Boolean, Exception> discountDiscoveryUnsafeSupplier) {

		try {
			discountDiscovery = discountDiscoveryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean discountDiscovery;

	public BigDecimal getDiscountLevel1() {
		return discountLevel1;
	}

	public void setDiscountLevel1(BigDecimal discountLevel1) {
		this.discountLevel1 = discountLevel1;
	}

	public void setDiscountLevel1(
		UnsafeSupplier<BigDecimal, Exception> discountLevel1UnsafeSupplier) {

		try {
			discountLevel1 = discountLevel1UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal discountLevel1;

	public BigDecimal getDiscountLevel2() {
		return discountLevel2;
	}

	public void setDiscountLevel2(BigDecimal discountLevel2) {
		this.discountLevel2 = discountLevel2;
	}

	public void setDiscountLevel2(
		UnsafeSupplier<BigDecimal, Exception> discountLevel2UnsafeSupplier) {

		try {
			discountLevel2 = discountLevel2UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal discountLevel2;

	public BigDecimal getDiscountLevel3() {
		return discountLevel3;
	}

	public void setDiscountLevel3(BigDecimal discountLevel3) {
		this.discountLevel3 = discountLevel3;
	}

	public void setDiscountLevel3(
		UnsafeSupplier<BigDecimal, Exception> discountLevel3UnsafeSupplier) {

		try {
			discountLevel3 = discountLevel3UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal discountLevel3;

	public BigDecimal getDiscountLevel4() {
		return discountLevel4;
	}

	public void setDiscountLevel4(BigDecimal discountLevel4) {
		this.discountLevel4 = discountLevel4;
	}

	public void setDiscountLevel4(
		UnsafeSupplier<BigDecimal, Exception> discountLevel4UnsafeSupplier) {

		try {
			discountLevel4 = discountLevel4UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal discountLevel4;

	public String getDiscountLevelsFormatted() {
		return discountLevelsFormatted;
	}

	public void setDiscountLevelsFormatted(String discountLevelsFormatted) {
		this.discountLevelsFormatted = discountLevelsFormatted;
	}

	public void setDiscountLevelsFormatted(
		UnsafeSupplier<String, Exception>
			discountLevelsFormattedUnsafeSupplier) {

		try {
			discountLevelsFormatted =
				discountLevelsFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String discountLevelsFormatted;

	public Date getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;
	}

	public void setDisplayDate(
		UnsafeSupplier<Date, Exception> displayDateUnsafeSupplier) {

		try {
			displayDate = displayDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date displayDate;

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setExpirationDate(
		UnsafeSupplier<Date, Exception> expirationDateUnsafeSupplier) {

		try {
			expirationDate = expirationDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date expirationDate;

	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String externalReferenceCode;

	public Boolean getHasTierPrice() {
		return hasTierPrice;
	}

	public void setHasTierPrice(Boolean hasTierPrice) {
		this.hasTierPrice = hasTierPrice;
	}

	public void setHasTierPrice(
		UnsafeSupplier<Boolean, Exception> hasTierPriceUnsafeSupplier) {

		try {
			hasTierPrice = hasTierPriceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean hasTierPrice;

	public Boolean getNeverExpire() {
		return neverExpire;
	}

	public void setNeverExpire(Boolean neverExpire) {
		this.neverExpire = neverExpire;
	}

	public void setNeverExpire(
		UnsafeSupplier<Boolean, Exception> neverExpireUnsafeSupplier) {

		try {
			neverExpire = neverExpireUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean neverExpire;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setPrice(
		UnsafeSupplier<Double, Exception> priceUnsafeSupplier) {

		try {
			price = priceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double price;

	public Long getPriceEntryId() {
		return priceEntryId;
	}

	public void setPriceEntryId(Long priceEntryId) {
		this.priceEntryId = priceEntryId;
	}

	public void setPriceEntryId(
		UnsafeSupplier<Long, Exception> priceEntryIdUnsafeSupplier) {

		try {
			priceEntryId = priceEntryIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long priceEntryId;

	public String getPriceFormatted() {
		return priceFormatted;
	}

	public void setPriceFormatted(String priceFormatted) {
		this.priceFormatted = priceFormatted;
	}

	public void setPriceFormatted(
		UnsafeSupplier<String, Exception> priceFormattedUnsafeSupplier) {

		try {
			priceFormatted = priceFormattedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String priceFormatted;

	public String getPriceListExternalReferenceCode() {
		return priceListExternalReferenceCode;
	}

	public void setPriceListExternalReferenceCode(
		String priceListExternalReferenceCode) {

		this.priceListExternalReferenceCode = priceListExternalReferenceCode;
	}

	public void setPriceListExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			priceListExternalReferenceCodeUnsafeSupplier) {

		try {
			priceListExternalReferenceCode =
				priceListExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String priceListExternalReferenceCode;

	public Long getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(Long priceListId) {
		this.priceListId = priceListId;
	}

	public void setPriceListId(
		UnsafeSupplier<Long, Exception> priceListIdUnsafeSupplier) {

		try {
			priceListId = priceListIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long priceListId;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setProduct(
		UnsafeSupplier<Product, Exception> productUnsafeSupplier) {

		try {
			product = productUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Product product;

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public void setSku(UnsafeSupplier<Sku, Exception> skuUnsafeSupplier) {
		try {
			sku = skuUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Sku sku;

	public String getSkuExternalReferenceCode() {
		return skuExternalReferenceCode;
	}

	public void setSkuExternalReferenceCode(String skuExternalReferenceCode) {
		this.skuExternalReferenceCode = skuExternalReferenceCode;
	}

	public void setSkuExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			skuExternalReferenceCodeUnsafeSupplier) {

		try {
			skuExternalReferenceCode =
				skuExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String skuExternalReferenceCode;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public void setSkuId(UnsafeSupplier<Long, Exception> skuIdUnsafeSupplier) {
		try {
			skuId = skuIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long skuId;

	public TierPrice[] getTierPrices() {
		return tierPrices;
	}

	public void setTierPrices(TierPrice[] tierPrices) {
		this.tierPrices = tierPrices;
	}

	public void setTierPrices(
		UnsafeSupplier<TierPrice[], Exception> tierPricesUnsafeSupplier) {

		try {
			tierPrices = tierPricesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected TierPrice[] tierPrices;

	@Override
	public PriceEntry clone() throws CloneNotSupportedException {
		return (PriceEntry)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PriceEntry)) {
			return false;
		}

		PriceEntry priceEntry = (PriceEntry)object;

		return Objects.equals(toString(), priceEntry.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PriceEntrySerDes.toJSON(this);
	}

}