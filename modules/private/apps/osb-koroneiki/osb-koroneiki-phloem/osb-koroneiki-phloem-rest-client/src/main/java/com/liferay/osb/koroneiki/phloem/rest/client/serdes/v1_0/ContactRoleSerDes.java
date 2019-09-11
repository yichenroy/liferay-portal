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

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ContactRole;
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
public class ContactRoleSerDes {

	public static ContactRole toDTO(String json) {
		ContactRoleJSONParser contactRoleJSONParser =
			new ContactRoleJSONParser();

		return contactRoleJSONParser.parseToDTO(json);
	}

	public static ContactRole[] toDTOs(String json) {
		ContactRoleJSONParser contactRoleJSONParser =
			new ContactRoleJSONParser();

		return contactRoleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContactRole contactRole) {
		if (contactRole == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (contactRole.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(contactRole.getDateCreated()));

			sb.append("\"");
		}

		if (contactRole.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(contactRole.getDateModified()));

			sb.append("\"");
		}

		if (contactRole.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(contactRole.getDescription()));

			sb.append("\"");
		}

		if (contactRole.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(contactRole.getKey()));

			sb.append("\"");
		}

		if (contactRole.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(contactRole.getName()));

			sb.append("\"");
		}

		if (contactRole.getSystem() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"system\": ");

			sb.append(contactRole.getSystem());
		}

		if (contactRole.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(contactRole.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContactRoleJSONParser contactRoleJSONParser =
			new ContactRoleJSONParser();

		return contactRoleJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ContactRole contactRole) {
		if (contactRole == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(contactRole.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(contactRole.getDateModified()));

		if (contactRole.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(contactRole.getDescription()));
		}

		if (contactRole.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(contactRole.getKey()));
		}

		if (contactRole.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(contactRole.getName()));
		}

		if (contactRole.getSystem() == null) {
			map.put("system", null);
		}
		else {
			map.put("system", String.valueOf(contactRole.getSystem()));
		}

		if (contactRole.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(contactRole.getType()));
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

	private static class ContactRoleJSONParser
		extends BaseJSONParser<ContactRole> {

		@Override
		protected ContactRole createDTO() {
			return new ContactRole();
		}

		@Override
		protected ContactRole[] createDTOArray(int size) {
			return new ContactRole[size];
		}

		@Override
		protected void setField(
			ContactRole contactRole, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					contactRole.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					contactRole.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					contactRole.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					contactRole.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					contactRole.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "system")) {
				if (jsonParserFieldValue != null) {
					contactRole.setSystem((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					contactRole.setType(
						ContactRole.Type.create((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}