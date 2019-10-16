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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.SLAResult;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class SLAResultSerDes {

	public static SLAResult toDTO(String json) {
		SLAResultJSONParser slaResultJSONParser = new SLAResultJSONParser();

		return slaResultJSONParser.parseToDTO(json);
	}

	public static SLAResult[] toDTOs(String json) {
		SLAResultJSONParser slaResultJSONParser = new SLAResultJSONParser();

		return slaResultJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SLAResult slaResult) {
		if (slaResult == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (slaResult.getDateOverdue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateOverdue\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(slaResult.getDateOverdue()));

			sb.append("\"");
		}

		if (slaResult.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(slaResult.getId());
		}

		if (slaResult.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(slaResult.getName()));

			sb.append("\"");
		}

		if (slaResult.getOnTime() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onTime\": ");

			sb.append(slaResult.getOnTime());
		}

		if (slaResult.getRemainingTime() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"remainingTime\": ");

			sb.append(slaResult.getRemainingTime());
		}

		if (slaResult.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(slaResult.getStatus());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SLAResultJSONParser slaResultJSONParser = new SLAResultJSONParser();

		return slaResultJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SLAResult slaResult) {
		if (slaResult == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		map.put(
			"dateOverdue",
			liferayToJSONDateFormat.format(slaResult.getDateOverdue()));

		if (slaResult.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(slaResult.getId()));
		}

		if (slaResult.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(slaResult.getName()));
		}

		if (slaResult.getOnTime() == null) {
			map.put("onTime", null);
		}
		else {
			map.put("onTime", String.valueOf(slaResult.getOnTime()));
		}

		if (slaResult.getRemainingTime() == null) {
			map.put("remainingTime", null);
		}
		else {
			map.put(
				"remainingTime", String.valueOf(slaResult.getRemainingTime()));
		}

		if (slaResult.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(slaResult.getStatus()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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

	private static class SLAResultJSONParser extends BaseJSONParser<SLAResult> {

		@Override
		protected SLAResult createDTO() {
			return new SLAResult();
		}

		@Override
		protected SLAResult[] createDTOArray(int size) {
			return new SLAResult[size];
		}

		@Override
		protected void setField(
			SLAResult slaResult, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateOverdue")) {
				if (jsonParserFieldValue != null) {
					slaResult.setDateOverdue(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					slaResult.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					slaResult.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "onTime")) {
				if (jsonParserFieldValue != null) {
					slaResult.setOnTime((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "remainingTime")) {
				if (jsonParserFieldValue != null) {
					slaResult.setRemainingTime(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					slaResult.setStatus(
						SLAResult.Status.create((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}