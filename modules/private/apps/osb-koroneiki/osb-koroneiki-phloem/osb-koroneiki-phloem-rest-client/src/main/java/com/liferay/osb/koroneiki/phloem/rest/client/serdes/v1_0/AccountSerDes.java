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

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.PostalAddress;
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
public class AccountSerDes {

	public static Account toDTO(String json) {
		AccountJSONParser accountJSONParser = new AccountJSONParser();

		return accountJSONParser.parseToDTO(json);
	}

	public static Account[] toDTOs(String json) {
		AccountJSONParser accountJSONParser = new AccountJSONParser();

		return accountJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Account account) {
		if (account == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (account.getAddresses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addresses\": ");

			sb.append("[");

			for (int i = 0; i < account.getAddresses().length; i++) {
				sb.append(String.valueOf(account.getAddresses()[i]));

				if ((i + 1) < account.getAddresses().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (account.getCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"code\": ");

			sb.append("\"");

			sb.append(_escape(account.getCode()));

			sb.append("\"");
		}

		if (account.getContactEmailAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contactEmailAddress\": ");

			sb.append("\"");

			sb.append(_escape(account.getContactEmailAddress()));

			sb.append("\"");
		}

		if (account.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(account.getDateCreated()));

			sb.append("\"");
		}

		if (account.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(account.getDateModified()));

			sb.append("\"");
		}

		if (account.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(account.getDescription()));

			sb.append("\"");
		}

		if (account.getExternalLinks() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalLinks\": ");

			sb.append("[");

			for (int i = 0; i < account.getExternalLinks().length; i++) {
				sb.append(String.valueOf(account.getExternalLinks()[i]));

				if ((i + 1) < account.getExternalLinks().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (account.getFaxNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"faxNumber\": ");

			sb.append("\"");

			sb.append(_escape(account.getFaxNumber()));

			sb.append("\"");
		}

		if (account.getIndustry() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"industry\": ");

			sb.append("\"");

			sb.append(account.getIndustry());

			sb.append("\"");
		}

		if (account.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(account.getKey()));

			sb.append("\"");
		}

		if (account.getLogoId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"logoId\": ");

			sb.append(account.getLogoId());
		}

		if (account.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(account.getName()));

			sb.append("\"");
		}

		if (account.getNotes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"notes\": ");

			sb.append("\"");

			sb.append(_escape(account.getNotes()));

			sb.append("\"");
		}

		if (account.getParentAccountKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentAccountKey\": ");

			sb.append("\"");

			sb.append(_escape(account.getParentAccountKey()));

			sb.append("\"");
		}

		if (account.getPhoneNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"phoneNumber\": ");

			sb.append("\"");

			sb.append(_escape(account.getPhoneNumber()));

			sb.append("\"");
		}

		if (account.getProductPurchases() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productPurchases\": ");

			sb.append("[");

			for (int i = 0; i < account.getProductPurchases().length; i++) {
				sb.append(String.valueOf(account.getProductPurchases()[i]));

				if ((i + 1) < account.getProductPurchases().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (account.getProfileEmailAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"profileEmailAddress\": ");

			sb.append("\"");

			sb.append(_escape(account.getProfileEmailAddress()));

			sb.append("\"");
		}

		if (account.getSoldBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"soldBy\": ");

			sb.append("\"");

			sb.append(_escape(account.getSoldBy()));

			sb.append("\"");
		}

		if (account.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(account.getStatus());

			sb.append("\"");
		}

		if (account.getTier() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tier\": ");

			sb.append("\"");

			sb.append(account.getTier());

			sb.append("\"");
		}

		if (account.getWebsite() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"website\": ");

			sb.append("\"");

			sb.append(_escape(account.getWebsite()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AccountJSONParser accountJSONParser = new AccountJSONParser();

		return accountJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Account account) {
		if (account == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (account.getAddresses() == null) {
			map.put("addresses", null);
		}
		else {
			map.put("addresses", String.valueOf(account.getAddresses()));
		}

		if (account.getCode() == null) {
			map.put("code", null);
		}
		else {
			map.put("code", String.valueOf(account.getCode()));
		}

		if (account.getContactEmailAddress() == null) {
			map.put("contactEmailAddress", null);
		}
		else {
			map.put(
				"contactEmailAddress",
				String.valueOf(account.getContactEmailAddress()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(account.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(account.getDateModified()));

		if (account.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(account.getDescription()));
		}

		if (account.getExternalLinks() == null) {
			map.put("externalLinks", null);
		}
		else {
			map.put(
				"externalLinks", String.valueOf(account.getExternalLinks()));
		}

		if (account.getFaxNumber() == null) {
			map.put("faxNumber", null);
		}
		else {
			map.put("faxNumber", String.valueOf(account.getFaxNumber()));
		}

		if (account.getIndustry() == null) {
			map.put("industry", null);
		}
		else {
			map.put("industry", String.valueOf(account.getIndustry()));
		}

		if (account.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(account.getKey()));
		}

		if (account.getLogoId() == null) {
			map.put("logoId", null);
		}
		else {
			map.put("logoId", String.valueOf(account.getLogoId()));
		}

		if (account.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(account.getName()));
		}

		if (account.getNotes() == null) {
			map.put("notes", null);
		}
		else {
			map.put("notes", String.valueOf(account.getNotes()));
		}

		if (account.getParentAccountKey() == null) {
			map.put("parentAccountKey", null);
		}
		else {
			map.put(
				"parentAccountKey",
				String.valueOf(account.getParentAccountKey()));
		}

		if (account.getPhoneNumber() == null) {
			map.put("phoneNumber", null);
		}
		else {
			map.put("phoneNumber", String.valueOf(account.getPhoneNumber()));
		}

		if (account.getProductPurchases() == null) {
			map.put("productPurchases", null);
		}
		else {
			map.put(
				"productPurchases",
				String.valueOf(account.getProductPurchases()));
		}

		if (account.getProfileEmailAddress() == null) {
			map.put("profileEmailAddress", null);
		}
		else {
			map.put(
				"profileEmailAddress",
				String.valueOf(account.getProfileEmailAddress()));
		}

		if (account.getSoldBy() == null) {
			map.put("soldBy", null);
		}
		else {
			map.put("soldBy", String.valueOf(account.getSoldBy()));
		}

		if (account.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(account.getStatus()));
		}

		if (account.getTier() == null) {
			map.put("tier", null);
		}
		else {
			map.put("tier", String.valueOf(account.getTier()));
		}

		if (account.getWebsite() == null) {
			map.put("website", null);
		}
		else {
			map.put("website", String.valueOf(account.getWebsite()));
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

	private static class AccountJSONParser extends BaseJSONParser<Account> {

		@Override
		protected Account createDTO() {
			return new Account();
		}

		@Override
		protected Account[] createDTOArray(int size) {
			return new Account[size];
		}

		@Override
		protected void setField(
			Account account, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "addresses")) {
				if (jsonParserFieldValue != null) {
					account.setAddresses(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PostalAddressSerDes.toDTO((String)object)
						).toArray(
							size -> new PostalAddress[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "code")) {
				if (jsonParserFieldValue != null) {
					account.setCode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "contactEmailAddress")) {

				if (jsonParserFieldValue != null) {
					account.setContactEmailAddress(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					account.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					account.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					account.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "externalLinks")) {
				if (jsonParserFieldValue != null) {
					account.setExternalLinks(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ExternalLinkSerDes.toDTO((String)object)
						).toArray(
							size -> new ExternalLink[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "faxNumber")) {
				if (jsonParserFieldValue != null) {
					account.setFaxNumber((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "industry")) {
				if (jsonParserFieldValue != null) {
					account.setIndustry(
						Account.Industry.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					account.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "logoId")) {
				if (jsonParserFieldValue != null) {
					account.setLogoId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					account.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "notes")) {
				if (jsonParserFieldValue != null) {
					account.setNotes((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "parentAccountKey")) {
				if (jsonParserFieldValue != null) {
					account.setParentAccountKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "phoneNumber")) {
				if (jsonParserFieldValue != null) {
					account.setPhoneNumber((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productPurchases")) {
				if (jsonParserFieldValue != null) {
					account.setProductPurchases(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ProductPurchaseSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ProductPurchase[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "profileEmailAddress")) {

				if (jsonParserFieldValue != null) {
					account.setProfileEmailAddress(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "soldBy")) {
				if (jsonParserFieldValue != null) {
					account.setSoldBy((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					account.setStatus(
						Account.Status.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "tier")) {
				if (jsonParserFieldValue != null) {
					account.setTier(
						Account.Tier.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "website")) {
				if (jsonParserFieldValue != null) {
					account.setWebsite((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}