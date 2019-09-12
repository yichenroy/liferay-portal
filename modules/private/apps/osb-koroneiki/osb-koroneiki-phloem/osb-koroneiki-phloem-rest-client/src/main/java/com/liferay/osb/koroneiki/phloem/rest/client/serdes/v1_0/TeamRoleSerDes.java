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

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.TeamRole;
import com.liferay.osb.koroneiki.phloem.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
public class TeamRoleSerDes {

	public static TeamRole toDTO(String json) {
		TeamRoleJSONParser teamRoleJSONParser = new TeamRoleJSONParser();

		return teamRoleJSONParser.parseToDTO(json);
	}

	public static TeamRole[] toDTOs(String json) {
		TeamRoleJSONParser teamRoleJSONParser = new TeamRoleJSONParser();

		return teamRoleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TeamRole teamRole) {
		if (teamRole == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (teamRole.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(teamRole.getDateCreated()));

			sb.append("\"");
		}

		if (teamRole.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(teamRole.getDateModified()));

			sb.append("\"");
		}

		if (teamRole.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(teamRole.getDescription()));

			sb.append("\"");
		}

		if (teamRole.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(teamRole.getKey()));

			sb.append("\"");
		}

		if (teamRole.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(teamRole.getName()));

			sb.append("\"");
		}

		if (teamRole.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(teamRole.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TeamRoleJSONParser teamRoleJSONParser = new TeamRoleJSONParser();

		return teamRoleJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(TeamRole teamRole) {
		if (teamRole == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(teamRole.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(teamRole.getDateModified()));

		if (teamRole.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(teamRole.getDescription()));
		}

		if (teamRole.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(teamRole.getKey()));
		}

		if (teamRole.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(teamRole.getName()));
		}

		if (teamRole.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(teamRole.getType()));
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

	private static class TeamRoleJSONParser extends BaseJSONParser<TeamRole> {

		@Override
		protected TeamRole createDTO() {
			return new TeamRole();
		}

		@Override
		protected TeamRole[] createDTOArray(int size) {
			return new TeamRole[size];
		}

		@Override
		protected void setField(
			TeamRole teamRole, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					teamRole.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					teamRole.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					teamRole.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					teamRole.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					teamRole.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					teamRole.setType(
						TeamRole.Type.create((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}