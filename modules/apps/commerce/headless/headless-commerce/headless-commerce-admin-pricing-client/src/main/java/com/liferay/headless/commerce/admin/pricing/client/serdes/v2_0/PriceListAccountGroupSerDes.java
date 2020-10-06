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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class PriceListAccountGroupSerDes {

	public static PriceListAccountGroup toDTO(String json) {
		PriceListAccountGroupJSONParser priceListAccountGroupJSONParser =
			new PriceListAccountGroupJSONParser();

		return priceListAccountGroupJSONParser.parseToDTO(json);
	}

	public static PriceListAccountGroup[] toDTOs(String json) {
		PriceListAccountGroupJSONParser priceListAccountGroupJSONParser =
			new PriceListAccountGroupJSONParser();

		return priceListAccountGroupJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PriceListAccountGroup priceListAccountGroup) {
		if (priceListAccountGroup == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (priceListAccountGroup.getAccountGroup() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountGroup\": ");

			sb.append(String.valueOf(priceListAccountGroup.getAccountGroup()));
		}

		if (priceListAccountGroup.getAccountGroupExternalReferenceCode() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountGroupExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					priceListAccountGroup.
						getAccountGroupExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceListAccountGroup.getAccountGroupId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountGroupId\": ");

			sb.append(priceListAccountGroup.getAccountGroupId());
		}

		if (priceListAccountGroup.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(priceListAccountGroup.getActions()));
		}

		if (priceListAccountGroup.getOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"order\": ");

			sb.append(priceListAccountGroup.getOrder());
		}

		if (priceListAccountGroup.getPriceListAccountGroupId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListAccountGroupId\": ");

			sb.append(priceListAccountGroup.getPriceListAccountGroupId());
		}

		if (priceListAccountGroup.getPriceListExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					priceListAccountGroup.getPriceListExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceListAccountGroup.getPriceListId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListId\": ");

			sb.append(priceListAccountGroup.getPriceListId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PriceListAccountGroupJSONParser priceListAccountGroupJSONParser =
			new PriceListAccountGroupJSONParser();

		return priceListAccountGroupJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PriceListAccountGroup priceListAccountGroup) {

		if (priceListAccountGroup == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (priceListAccountGroup.getAccountGroup() == null) {
			map.put("accountGroup", null);
		}
		else {
			map.put(
				"accountGroup",
				String.valueOf(priceListAccountGroup.getAccountGroup()));
		}

		if (priceListAccountGroup.getAccountGroupExternalReferenceCode() ==
				null) {

			map.put("accountGroupExternalReferenceCode", null);
		}
		else {
			map.put(
				"accountGroupExternalReferenceCode",
				String.valueOf(
					priceListAccountGroup.
						getAccountGroupExternalReferenceCode()));
		}

		if (priceListAccountGroup.getAccountGroupId() == null) {
			map.put("accountGroupId", null);
		}
		else {
			map.put(
				"accountGroupId",
				String.valueOf(priceListAccountGroup.getAccountGroupId()));
		}

		if (priceListAccountGroup.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put(
				"actions", String.valueOf(priceListAccountGroup.getActions()));
		}

		if (priceListAccountGroup.getOrder() == null) {
			map.put("order", null);
		}
		else {
			map.put("order", String.valueOf(priceListAccountGroup.getOrder()));
		}

		if (priceListAccountGroup.getPriceListAccountGroupId() == null) {
			map.put("priceListAccountGroupId", null);
		}
		else {
			map.put(
				"priceListAccountGroupId",
				String.valueOf(
					priceListAccountGroup.getPriceListAccountGroupId()));
		}

		if (priceListAccountGroup.getPriceListExternalReferenceCode() == null) {
			map.put("priceListExternalReferenceCode", null);
		}
		else {
			map.put(
				"priceListExternalReferenceCode",
				String.valueOf(
					priceListAccountGroup.getPriceListExternalReferenceCode()));
		}

		if (priceListAccountGroup.getPriceListId() == null) {
			map.put("priceListId", null);
		}
		else {
			map.put(
				"priceListId",
				String.valueOf(priceListAccountGroup.getPriceListId()));
		}

		return map;
	}

	public static class PriceListAccountGroupJSONParser
		extends BaseJSONParser<PriceListAccountGroup> {

		@Override
		protected PriceListAccountGroup createDTO() {
			return new PriceListAccountGroup();
		}

		@Override
		protected PriceListAccountGroup[] createDTOArray(int size) {
			return new PriceListAccountGroup[size];
		}

		@Override
		protected void setField(
			PriceListAccountGroup priceListAccountGroup,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "accountGroup")) {
				if (jsonParserFieldValue != null) {
					priceListAccountGroup.setAccountGroup(
						AccountGroupSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"accountGroupExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceListAccountGroup.setAccountGroupExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "accountGroupId")) {
				if (jsonParserFieldValue != null) {
					priceListAccountGroup.setAccountGroupId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					priceListAccountGroup.setActions(
						(Map)PriceListAccountGroupSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "order")) {
				if (jsonParserFieldValue != null) {
					priceListAccountGroup.setOrder(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "priceListAccountGroupId")) {

				if (jsonParserFieldValue != null) {
					priceListAccountGroup.setPriceListAccountGroupId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"priceListExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceListAccountGroup.setPriceListExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceListId")) {
				if (jsonParserFieldValue != null) {
					priceListAccountGroup.setPriceListId(
						Long.valueOf((String)jsonParserFieldValue));
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