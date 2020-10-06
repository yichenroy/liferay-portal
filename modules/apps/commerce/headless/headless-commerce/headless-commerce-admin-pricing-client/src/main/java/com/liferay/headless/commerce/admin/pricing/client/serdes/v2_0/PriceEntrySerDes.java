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

package com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.TierPrice;
import com.liferay.headless.commerce.admin.pricing.client.json.BaseJSONParser;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class PriceEntrySerDes {

	public static PriceEntry toDTO(String json) {
		PriceEntryJSONParser priceEntryJSONParser = new PriceEntryJSONParser();

		return priceEntryJSONParser.parseToDTO(json);
	}

	public static PriceEntry[] toDTOs(String json) {
		PriceEntryJSONParser priceEntryJSONParser = new PriceEntryJSONParser();

		return priceEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PriceEntry priceEntry) {
		if (priceEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (priceEntry.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(priceEntry.getActions()));
		}

		if (priceEntry.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(priceEntry.getActive());
		}

		if (priceEntry.getBulkPricing() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bulkPricing\": ");

			sb.append(priceEntry.getBulkPricing());
		}

		if (priceEntry.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(priceEntry.getCustomFields()));
		}

		if (priceEntry.getDiscountDiscovery() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountDiscovery\": ");

			sb.append(priceEntry.getDiscountDiscovery());
		}

		if (priceEntry.getDiscountLevel1() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountLevel1\": ");

			sb.append(priceEntry.getDiscountLevel1());
		}

		if (priceEntry.getDiscountLevel2() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountLevel2\": ");

			sb.append(priceEntry.getDiscountLevel2());
		}

		if (priceEntry.getDiscountLevel3() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountLevel3\": ");

			sb.append(priceEntry.getDiscountLevel3());
		}

		if (priceEntry.getDiscountLevel4() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountLevel4\": ");

			sb.append(priceEntry.getDiscountLevel4());
		}

		if (priceEntry.getDiscountLevelsFormatted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountLevelsFormatted\": ");

			sb.append("\"");

			sb.append(_escape(priceEntry.getDiscountLevelsFormatted()));

			sb.append("\"");
		}

		if (priceEntry.getDisplayDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(priceEntry.getDisplayDate()));

			sb.append("\"");
		}

		if (priceEntry.getExpirationDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expirationDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(priceEntry.getExpirationDate()));

			sb.append("\"");
		}

		if (priceEntry.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(priceEntry.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceEntry.getHasTierPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hasTierPrice\": ");

			sb.append(priceEntry.getHasTierPrice());
		}

		if (priceEntry.getNeverExpire() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(priceEntry.getNeverExpire());
		}

		if (priceEntry.getPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append(priceEntry.getPrice());
		}

		if (priceEntry.getPriceEntryId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceEntryId\": ");

			sb.append(priceEntry.getPriceEntryId());
		}

		if (priceEntry.getPriceFormatted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceFormatted\": ");

			sb.append("\"");

			sb.append(_escape(priceEntry.getPriceFormatted()));

			sb.append("\"");
		}

		if (priceEntry.getPriceListExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(priceEntry.getPriceListExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceEntry.getPriceListId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListId\": ");

			sb.append(priceEntry.getPriceListId());
		}

		if (priceEntry.getProduct() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"product\": ");

			sb.append(String.valueOf(priceEntry.getProduct()));
		}

		if (priceEntry.getSku() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append(String.valueOf(priceEntry.getSku()));
		}

		if (priceEntry.getSkuExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(priceEntry.getSkuExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceEntry.getSkuId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuId\": ");

			sb.append(priceEntry.getSkuId());
		}

		if (priceEntry.getTierPrices() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tierPrices\": ");

			sb.append("[");

			for (int i = 0; i < priceEntry.getTierPrices().length; i++) {
				sb.append(String.valueOf(priceEntry.getTierPrices()[i]));

				if ((i + 1) < priceEntry.getTierPrices().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PriceEntryJSONParser priceEntryJSONParser = new PriceEntryJSONParser();

		return priceEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PriceEntry priceEntry) {
		if (priceEntry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (priceEntry.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(priceEntry.getActions()));
		}

		if (priceEntry.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(priceEntry.getActive()));
		}

		if (priceEntry.getBulkPricing() == null) {
			map.put("bulkPricing", null);
		}
		else {
			map.put("bulkPricing", String.valueOf(priceEntry.getBulkPricing()));
		}

		if (priceEntry.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields", String.valueOf(priceEntry.getCustomFields()));
		}

		if (priceEntry.getDiscountDiscovery() == null) {
			map.put("discountDiscovery", null);
		}
		else {
			map.put(
				"discountDiscovery",
				String.valueOf(priceEntry.getDiscountDiscovery()));
		}

		if (priceEntry.getDiscountLevel1() == null) {
			map.put("discountLevel1", null);
		}
		else {
			map.put(
				"discountLevel1",
				String.valueOf(priceEntry.getDiscountLevel1()));
		}

		if (priceEntry.getDiscountLevel2() == null) {
			map.put("discountLevel2", null);
		}
		else {
			map.put(
				"discountLevel2",
				String.valueOf(priceEntry.getDiscountLevel2()));
		}

		if (priceEntry.getDiscountLevel3() == null) {
			map.put("discountLevel3", null);
		}
		else {
			map.put(
				"discountLevel3",
				String.valueOf(priceEntry.getDiscountLevel3()));
		}

		if (priceEntry.getDiscountLevel4() == null) {
			map.put("discountLevel4", null);
		}
		else {
			map.put(
				"discountLevel4",
				String.valueOf(priceEntry.getDiscountLevel4()));
		}

		if (priceEntry.getDiscountLevelsFormatted() == null) {
			map.put("discountLevelsFormatted", null);
		}
		else {
			map.put(
				"discountLevelsFormatted",
				String.valueOf(priceEntry.getDiscountLevelsFormatted()));
		}

		if (priceEntry.getDisplayDate() == null) {
			map.put("displayDate", null);
		}
		else {
			map.put(
				"displayDate",
				liferayToJSONDateFormat.format(priceEntry.getDisplayDate()));
		}

		if (priceEntry.getExpirationDate() == null) {
			map.put("expirationDate", null);
		}
		else {
			map.put(
				"expirationDate",
				liferayToJSONDateFormat.format(priceEntry.getExpirationDate()));
		}

		if (priceEntry.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(priceEntry.getExternalReferenceCode()));
		}

		if (priceEntry.getHasTierPrice() == null) {
			map.put("hasTierPrice", null);
		}
		else {
			map.put(
				"hasTierPrice", String.valueOf(priceEntry.getHasTierPrice()));
		}

		if (priceEntry.getNeverExpire() == null) {
			map.put("neverExpire", null);
		}
		else {
			map.put("neverExpire", String.valueOf(priceEntry.getNeverExpire()));
		}

		if (priceEntry.getPrice() == null) {
			map.put("price", null);
		}
		else {
			map.put("price", String.valueOf(priceEntry.getPrice()));
		}

		if (priceEntry.getPriceEntryId() == null) {
			map.put("priceEntryId", null);
		}
		else {
			map.put(
				"priceEntryId", String.valueOf(priceEntry.getPriceEntryId()));
		}

		if (priceEntry.getPriceFormatted() == null) {
			map.put("priceFormatted", null);
		}
		else {
			map.put(
				"priceFormatted",
				String.valueOf(priceEntry.getPriceFormatted()));
		}

		if (priceEntry.getPriceListExternalReferenceCode() == null) {
			map.put("priceListExternalReferenceCode", null);
		}
		else {
			map.put(
				"priceListExternalReferenceCode",
				String.valueOf(priceEntry.getPriceListExternalReferenceCode()));
		}

		if (priceEntry.getPriceListId() == null) {
			map.put("priceListId", null);
		}
		else {
			map.put("priceListId", String.valueOf(priceEntry.getPriceListId()));
		}

		if (priceEntry.getProduct() == null) {
			map.put("product", null);
		}
		else {
			map.put("product", String.valueOf(priceEntry.getProduct()));
		}

		if (priceEntry.getSku() == null) {
			map.put("sku", null);
		}
		else {
			map.put("sku", String.valueOf(priceEntry.getSku()));
		}

		if (priceEntry.getSkuExternalReferenceCode() == null) {
			map.put("skuExternalReferenceCode", null);
		}
		else {
			map.put(
				"skuExternalReferenceCode",
				String.valueOf(priceEntry.getSkuExternalReferenceCode()));
		}

		if (priceEntry.getSkuId() == null) {
			map.put("skuId", null);
		}
		else {
			map.put("skuId", String.valueOf(priceEntry.getSkuId()));
		}

		if (priceEntry.getTierPrices() == null) {
			map.put("tierPrices", null);
		}
		else {
			map.put("tierPrices", String.valueOf(priceEntry.getTierPrices()));
		}

		return map;
	}

	public static class PriceEntryJSONParser
		extends BaseJSONParser<PriceEntry> {

		@Override
		protected PriceEntry createDTO() {
			return new PriceEntry();
		}

		@Override
		protected PriceEntry[] createDTOArray(int size) {
			return new PriceEntry[size];
		}

		@Override
		protected void setField(
			PriceEntry priceEntry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setActions(
						(Map)PriceEntrySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "bulkPricing")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setBulkPricing((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setCustomFields(
						(Map)PriceEntrySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountDiscovery")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setDiscountDiscovery(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountLevel1")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setDiscountLevel1(
						(BigDecimal)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountLevel2")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setDiscountLevel2(
						(BigDecimal)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountLevel3")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setDiscountLevel3(
						(BigDecimal)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountLevel4")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setDiscountLevel4(
						(BigDecimal)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "discountLevelsFormatted")) {

				if (jsonParserFieldValue != null) {
					priceEntry.setDiscountLevelsFormatted(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayDate")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setDisplayDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expirationDate")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setExpirationDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceEntry.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "hasTierPrice")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setHasTierPrice((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "neverExpire")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setNeverExpire((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "price")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setPrice(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceEntryId")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setPriceEntryId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceFormatted")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setPriceFormatted((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"priceListExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceEntry.setPriceListExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceListId")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setPriceListId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "product")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setProduct(
						ProductSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sku")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setSku(
						SkuSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "skuExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceEntry.setSkuExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuId")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setSkuId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "tierPrices")) {
				if (jsonParserFieldValue != null) {
					priceEntry.setTierPrices(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TierPriceSerDes.toDTO((String)object)
						).toArray(
							size -> new TierPrice[size]
						));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}