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

package com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
public class ProductPurchaseSerDes {

	public static ProductPurchase toDTO(String json) {
		ProductPurchaseJSONParser productPurchaseJSONParser =
			new ProductPurchaseJSONParser();

		return productPurchaseJSONParser.parseToDTO(json);
	}

	public static ProductPurchase[] toDTOs(String json) {
		ProductPurchaseJSONParser productPurchaseJSONParser =
			new ProductPurchaseJSONParser();

		return productPurchaseJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ProductPurchase productPurchase) {
		if (productPurchase == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (productPurchase.getAccountKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountKey\": ");

			sb.append("\"");

			sb.append(_escape(productPurchase.getAccountKey()));

			sb.append("\"");
		}

		if (productPurchase.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					productPurchase.getDateCreated()));

			sb.append("\"");
		}

		if (productPurchase.getEndDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"endDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(productPurchase.getEndDate()));

			sb.append("\"");
		}

		if (productPurchase.getExternalLinks() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalLinks\": ");

			sb.append("[");

			for (int i = 0; i < productPurchase.getExternalLinks().length;
				 i++) {

				sb.append(
					String.valueOf(productPurchase.getExternalLinks()[i]));

				if ((i + 1) < productPurchase.getExternalLinks().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (productPurchase.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(productPurchase.getKey()));

			sb.append("\"");
		}

		if (productPurchase.getPerpetual() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"perpetual\": ");

			sb.append(productPurchase.getPerpetual());
		}

		if (productPurchase.getProduct() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"product\": ");

			sb.append(String.valueOf(productPurchase.getProduct()));
		}

		if (productPurchase.getProductKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productKey\": ");

			sb.append("\"");

			sb.append(_escape(productPurchase.getProductKey()));

			sb.append("\"");
		}

		if (productPurchase.getProperties() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"properties\": ");

			sb.append(_toJSON(productPurchase.getProperties()));
		}

		if (productPurchase.getQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"quantity\": ");

			sb.append(productPurchase.getQuantity());
		}

		if (productPurchase.getStartDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"startDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(productPurchase.getStartDate()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProductPurchaseJSONParser productPurchaseJSONParser =
			new ProductPurchaseJSONParser();

		return productPurchaseJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ProductPurchase productPurchase) {
		if (productPurchase == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (productPurchase.getAccountKey() == null) {
			map.put("accountKey", null);
		}
		else {
			map.put(
				"accountKey", String.valueOf(productPurchase.getAccountKey()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(productPurchase.getDateCreated()));

		map.put(
			"endDate",
			liferayToJSONDateFormat.format(productPurchase.getEndDate()));

		if (productPurchase.getExternalLinks() == null) {
			map.put("externalLinks", null);
		}
		else {
			map.put(
				"externalLinks",
				String.valueOf(productPurchase.getExternalLinks()));
		}

		if (productPurchase.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(productPurchase.getKey()));
		}

		if (productPurchase.getPerpetual() == null) {
			map.put("perpetual", null);
		}
		else {
			map.put(
				"perpetual", String.valueOf(productPurchase.getPerpetual()));
		}

		if (productPurchase.getProduct() == null) {
			map.put("product", null);
		}
		else {
			map.put("product", String.valueOf(productPurchase.getProduct()));
		}

		if (productPurchase.getProductKey() == null) {
			map.put("productKey", null);
		}
		else {
			map.put(
				"productKey", String.valueOf(productPurchase.getProductKey()));
		}

		if (productPurchase.getProperties() == null) {
			map.put("properties", null);
		}
		else {
			map.put(
				"properties", String.valueOf(productPurchase.getProperties()));
		}

		if (productPurchase.getQuantity() == null) {
			map.put("quantity", null);
		}
		else {
			map.put("quantity", String.valueOf(productPurchase.getQuantity()));
		}

		map.put(
			"startDate",
			liferayToJSONDateFormat.format(productPurchase.getStartDate()));

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ProductPurchaseJSONParser
		extends BaseJSONParser<ProductPurchase> {

		@Override
		protected ProductPurchase createDTO() {
			return new ProductPurchase();
		}

		@Override
		protected ProductPurchase[] createDTOArray(int size) {
			return new ProductPurchase[size];
		}

		@Override
		protected void setField(
			ProductPurchase productPurchase, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "accountKey")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setAccountKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "endDate")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setEndDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "externalLinks")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setExternalLinks(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ExternalLinkSerDes.toDTO((String)object)
						).toArray(
							size -> new ExternalLink[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "perpetual")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setPerpetual((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "product")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setProduct(
						ProductSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productKey")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setProductKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "properties")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setProperties(
						(Map)ProductPurchaseSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "quantity")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "startDate")) {
				if (jsonParserFieldValue != null) {
					productPurchase.setStartDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}