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

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.AuditEntry;
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
public class AuditEntrySerDes {

	public static AuditEntry toDTO(String json) {
		AuditEntryJSONParser auditEntryJSONParser = new AuditEntryJSONParser();

		return auditEntryJSONParser.parseToDTO(json);
	}

	public static AuditEntry[] toDTOs(String json) {
		AuditEntryJSONParser auditEntryJSONParser = new AuditEntryJSONParser();

		return auditEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AuditEntry auditEntry) {
		if (auditEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (auditEntry.getAction() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"action\": ");

			sb.append("\"");

			sb.append(auditEntry.getAction());

			sb.append("\"");
		}

		if (auditEntry.getAuditSetId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"auditSetId\": ");

			sb.append(auditEntry.getAuditSetId());
		}

		if (auditEntry.getClassName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"className\": ");

			sb.append("\"");

			sb.append(_escape(auditEntry.getClassName()));

			sb.append("\"");
		}

		if (auditEntry.getClassPK() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"classPK\": ");

			sb.append(auditEntry.getClassPK());
		}

		if (auditEntry.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(auditEntry.getDateCreated()));

			sb.append("\"");
		}

		if (auditEntry.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(auditEntry.getDescription()));

			sb.append("\"");
		}

		if (auditEntry.getField() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"field\": ");

			sb.append("\"");

			sb.append(_escape(auditEntry.getField()));

			sb.append("\"");
		}

		if (auditEntry.getFieldClassName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldClassName\": ");

			sb.append("\"");

			sb.append(_escape(auditEntry.getFieldClassName()));

			sb.append("\"");
		}

		if (auditEntry.getFieldClassPK() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldClassPK\": ");

			sb.append(auditEntry.getFieldClassPK());
		}

		if (auditEntry.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(auditEntry.getKey()));

			sb.append("\"");
		}

		if (auditEntry.getNewValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"newValue\": ");

			sb.append("\"");

			sb.append(_escape(auditEntry.getNewValue()));

			sb.append("\"");
		}

		if (auditEntry.getOldValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"oldValue\": ");

			sb.append("\"");

			sb.append(_escape(auditEntry.getOldValue()));

			sb.append("\"");
		}

		if (auditEntry.getUserId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(auditEntry.getUserId());
		}

		if (auditEntry.getUserName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(auditEntry.getUserName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AuditEntryJSONParser auditEntryJSONParser = new AuditEntryJSONParser();

		return auditEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AuditEntry auditEntry) {
		if (auditEntry == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (auditEntry.getAction() == null) {
			map.put("action", null);
		}
		else {
			map.put("action", String.valueOf(auditEntry.getAction()));
		}

		if (auditEntry.getAuditSetId() == null) {
			map.put("auditSetId", null);
		}
		else {
			map.put("auditSetId", String.valueOf(auditEntry.getAuditSetId()));
		}

		if (auditEntry.getClassName() == null) {
			map.put("className", null);
		}
		else {
			map.put("className", String.valueOf(auditEntry.getClassName()));
		}

		if (auditEntry.getClassPK() == null) {
			map.put("classPK", null);
		}
		else {
			map.put("classPK", String.valueOf(auditEntry.getClassPK()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(auditEntry.getDateCreated()));

		if (auditEntry.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(auditEntry.getDescription()));
		}

		if (auditEntry.getField() == null) {
			map.put("field", null);
		}
		else {
			map.put("field", String.valueOf(auditEntry.getField()));
		}

		if (auditEntry.getFieldClassName() == null) {
			map.put("fieldClassName", null);
		}
		else {
			map.put(
				"fieldClassName",
				String.valueOf(auditEntry.getFieldClassName()));
		}

		if (auditEntry.getFieldClassPK() == null) {
			map.put("fieldClassPK", null);
		}
		else {
			map.put(
				"fieldClassPK", String.valueOf(auditEntry.getFieldClassPK()));
		}

		if (auditEntry.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(auditEntry.getKey()));
		}

		if (auditEntry.getNewValue() == null) {
			map.put("newValue", null);
		}
		else {
			map.put("newValue", String.valueOf(auditEntry.getNewValue()));
		}

		if (auditEntry.getOldValue() == null) {
			map.put("oldValue", null);
		}
		else {
			map.put("oldValue", String.valueOf(auditEntry.getOldValue()));
		}

		if (auditEntry.getUserId() == null) {
			map.put("userId", null);
		}
		else {
			map.put("userId", String.valueOf(auditEntry.getUserId()));
		}

		if (auditEntry.getUserName() == null) {
			map.put("userName", null);
		}
		else {
			map.put("userName", String.valueOf(auditEntry.getUserName()));
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

	private static class AuditEntryJSONParser
		extends BaseJSONParser<AuditEntry> {

		@Override
		protected AuditEntry createDTO() {
			return new AuditEntry();
		}

		@Override
		protected AuditEntry[] createDTOArray(int size) {
			return new AuditEntry[size];
		}

		@Override
		protected void setField(
			AuditEntry auditEntry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "action")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setAction(
						AuditEntry.Action.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "auditSetId")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setAuditSetId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "className")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setClassName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "classPK")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setClassPK(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "field")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setField((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldClassName")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setFieldClassName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldClassPK")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setFieldClassPK(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "newValue")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setNewValue((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "oldValue")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setOldValue((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userId")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setUserId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userName")) {
				if (jsonParserFieldValue != null) {
					auditEntry.setUserName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}