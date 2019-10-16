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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Calendar;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class CalendarSerDes {

	public static Calendar toDTO(String json) {
		CalendarJSONParser calendarJSONParser = new CalendarJSONParser();

		return calendarJSONParser.parseToDTO(json);
	}

	public static Calendar[] toDTOs(String json) {
		CalendarJSONParser calendarJSONParser = new CalendarJSONParser();

		return calendarJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Calendar calendar) {
		if (calendar == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (calendar.getDefaultCalendar() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultCalendar\": ");

			sb.append(calendar.getDefaultCalendar());
		}

		if (calendar.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(calendar.getKey()));

			sb.append("\"");
		}

		if (calendar.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(calendar.getTitle()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CalendarJSONParser calendarJSONParser = new CalendarJSONParser();

		return calendarJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Calendar calendar) {
		if (calendar == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (calendar.getDefaultCalendar() == null) {
			map.put("defaultCalendar", null);
		}
		else {
			map.put(
				"defaultCalendar",
				String.valueOf(calendar.getDefaultCalendar()));
		}

		if (calendar.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(calendar.getKey()));
		}

		if (calendar.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(calendar.getTitle()));
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

	private static class CalendarJSONParser extends BaseJSONParser<Calendar> {

		@Override
		protected Calendar createDTO() {
			return new Calendar();
		}

		@Override
		protected Calendar[] createDTOArray(int size) {
			return new Calendar[size];
		}

		@Override
		protected void setField(
			Calendar calendar, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "defaultCalendar")) {
				if (jsonParserFieldValue != null) {
					calendar.setDefaultCalendar((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					calendar.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					calendar.setTitle((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}