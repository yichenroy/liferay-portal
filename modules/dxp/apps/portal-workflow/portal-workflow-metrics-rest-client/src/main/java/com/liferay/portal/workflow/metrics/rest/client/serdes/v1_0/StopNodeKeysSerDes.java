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

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.NodeKey;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.StopNodeKeys;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class StopNodeKeysSerDes {

	public static StopNodeKeys toDTO(String json) {
		StopNodeKeysJSONParser stopNodeKeysJSONParser =
			new StopNodeKeysJSONParser();

		return stopNodeKeysJSONParser.parseToDTO(json);
	}

	public static StopNodeKeys[] toDTOs(String json) {
		StopNodeKeysJSONParser stopNodeKeysJSONParser =
			new StopNodeKeysJSONParser();

		return stopNodeKeysJSONParser.parseToDTOs(json);
	}

	public static String toJSON(StopNodeKeys stopNodeKeys) {
		if (stopNodeKeys == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (stopNodeKeys.getNodeKeys() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nodeKeys\": ");

			sb.append("[");

			for (int i = 0; i < stopNodeKeys.getNodeKeys().length; i++) {
				sb.append(String.valueOf(stopNodeKeys.getNodeKeys()[i]));

				if ((i + 1) < stopNodeKeys.getNodeKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (stopNodeKeys.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(stopNodeKeys.getStatus());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		StopNodeKeysJSONParser stopNodeKeysJSONParser =
			new StopNodeKeysJSONParser();

		return stopNodeKeysJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(StopNodeKeys stopNodeKeys) {
		if (stopNodeKeys == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (stopNodeKeys.getNodeKeys() == null) {
			map.put("nodeKeys", null);
		}
		else {
			map.put("nodeKeys", String.valueOf(stopNodeKeys.getNodeKeys()));
		}

		if (stopNodeKeys.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(stopNodeKeys.getStatus()));
		}

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

			Object value = entry.getValue();

			Class valueClass = value.getClass();

			if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
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
			else {
				sb.append("\"");
				sb.append(entry.getValue());
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static class StopNodeKeysJSONParser
		extends BaseJSONParser<StopNodeKeys> {

		@Override
		protected StopNodeKeys createDTO() {
			return new StopNodeKeys();
		}

		@Override
		protected StopNodeKeys[] createDTOArray(int size) {
			return new StopNodeKeys[size];
		}

		@Override
		protected void setField(
			StopNodeKeys stopNodeKeys, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "nodeKeys")) {
				if (jsonParserFieldValue != null) {
					stopNodeKeys.setNodeKeys(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> NodeKeySerDes.toDTO((String)object)
						).toArray(
							size -> new NodeKey[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					stopNodeKeys.setStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}