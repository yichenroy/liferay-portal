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

package com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.client.function.UnsafeSupplier;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.AccountSerDes;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
public class Account {

	public static enum Industry {

		AEROSPACE_AND_DEFENSE("aerospace-and-defense"),
		AGRICULTURE("agriculture"), AUTOMOTIVE("automotive"),
		CONSULTING_MARKET_RESEARCH("consulting-market-research"),
		EDUCATION("education"), ENERGY("energy"), ENGINEERING("engineering"),
		FINANCIAL_SERVICES("financial-services"),
		FOOD_SERVICES("food-services"),
		GOVERNMENT_FEDERAL("government-federal"),
		GOVERNMENT_STATE_LOCAL("government-state-local"),
		HEALTHCARE("healthcare"), HOSPITALITY_LEISURE("hospitality-leisure"),
		INSURANCE("insurance"), MANUFACTURING("manufacturing"),
		MEDIA_ENTERTAINMENT("media-entertainment"),
		NOT_FOR_PROFIT_NGO("not-for-profit-ngo"), OTHER("other"),
		PHARMACEUTICALS("pharmaceuticals"),
		PROFESSIONAL_SERVICES_AGENCY_BUSINESS(
			"professional-services-agency-business"),
		PROFESSIONAL_SERVICES_TECHNICAL_WEB_IT(
			"professional-services-technical-web-it"),
		RETAIL_CONSUMER_PRODUCTS("retail-consumer-products"),
		TECHNOLOGY("technology"), TELECOMMUNICATION("telecommunication"),
		TRANSPORTATION("transportation"), UTILITIES("utilities");

		public static Industry create(String value) {
			for (Industry industry : values()) {
				if (Objects.equals(industry.getValue(), value)) {
					return industry;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Industry(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Status {

		APPROVED("approved"), CLOSED("closed"), EXPIRED("expired"),
		INACTIVE("inactive"), PENDING("pending"),
		PENDING_VALIDATION("pending-validation"), REJECTED("rejected");

		public static Status create(String value) {
			for (Status status : values()) {
				if (Objects.equals(status.getValue(), value)) {
					return status;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Status(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Tier {

		OEM("oem"), PREMIER("premier"), REGULAR("regular"),
		STRATEGIC("strategic");

		public static Tier create(String value) {
			for (Tier tier : values()) {
				if (Objects.equals(tier.getValue(), value)) {
					return tier;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Tier(String value) {
			_value = value;
		}

		private final String _value;

	}

	public PostalAddress[] getAddresses() {
		return addresses;
	}

	public void setAddresses(PostalAddress[] addresses) {
		this.addresses = addresses;
	}

	public void setAddresses(
		UnsafeSupplier<PostalAddress[], Exception> addressesUnsafeSupplier) {

		try {
			addresses = addressesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PostalAddress[] addresses;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCode(UnsafeSupplier<String, Exception> codeUnsafeSupplier) {
		try {
			code = codeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String code;

	public String getContactEmailAddress() {
		return contactEmailAddress;
	}

	public void setContactEmailAddress(String contactEmailAddress) {
		this.contactEmailAddress = contactEmailAddress;
	}

	public void setContactEmailAddress(
		UnsafeSupplier<String, Exception> contactEmailAddressUnsafeSupplier) {

		try {
			contactEmailAddress = contactEmailAddressUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String contactEmailAddress;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

	public ExternalLink[] getExternalLinks() {
		return externalLinks;
	}

	public void setExternalLinks(ExternalLink[] externalLinks) {
		this.externalLinks = externalLinks;
	}

	public void setExternalLinks(
		UnsafeSupplier<ExternalLink[], Exception> externalLinksUnsafeSupplier) {

		try {
			externalLinks = externalLinksUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ExternalLink[] externalLinks;

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public void setFaxNumber(
		UnsafeSupplier<String, Exception> faxNumberUnsafeSupplier) {

		try {
			faxNumber = faxNumberUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String faxNumber;

	public Industry getIndustry() {
		return industry;
	}

	public String getIndustryAsString() {
		if (industry == null) {
			return null;
		}

		return industry.toString();
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public void setIndustry(
		UnsafeSupplier<Industry, Exception> industryUnsafeSupplier) {

		try {
			industry = industryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Industry industry;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setKey(UnsafeSupplier<String, Exception> keyUnsafeSupplier) {
		try {
			key = keyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String key;

	public Long getLogoId() {
		return logoId;
	}

	public void setLogoId(Long logoId) {
		this.logoId = logoId;
	}

	public void setLogoId(
		UnsafeSupplier<Long, Exception> logoIdUnsafeSupplier) {

		try {
			logoId = logoIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long logoId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setNotes(
		UnsafeSupplier<String, Exception> notesUnsafeSupplier) {

		try {
			notes = notesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String notes;

	public String getParentAccountKey() {
		return parentAccountKey;
	}

	public void setParentAccountKey(String parentAccountKey) {
		this.parentAccountKey = parentAccountKey;
	}

	public void setParentAccountKey(
		UnsafeSupplier<String, Exception> parentAccountKeyUnsafeSupplier) {

		try {
			parentAccountKey = parentAccountKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String parentAccountKey;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPhoneNumber(
		UnsafeSupplier<String, Exception> phoneNumberUnsafeSupplier) {

		try {
			phoneNumber = phoneNumberUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String phoneNumber;

	public ProductPurchase[] getProductPurchases() {
		return productPurchases;
	}

	public void setProductPurchases(ProductPurchase[] productPurchases) {
		this.productPurchases = productPurchases;
	}

	public void setProductPurchases(
		UnsafeSupplier<ProductPurchase[], Exception>
			productPurchasesUnsafeSupplier) {

		try {
			productPurchases = productPurchasesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ProductPurchase[] productPurchases;

	public String getProfileEmailAddress() {
		return profileEmailAddress;
	}

	public void setProfileEmailAddress(String profileEmailAddress) {
		this.profileEmailAddress = profileEmailAddress;
	}

	public void setProfileEmailAddress(
		UnsafeSupplier<String, Exception> profileEmailAddressUnsafeSupplier) {

		try {
			profileEmailAddress = profileEmailAddressUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String profileEmailAddress;

	public String getSoldBy() {
		return soldBy;
	}

	public void setSoldBy(String soldBy) {
		this.soldBy = soldBy;
	}

	public void setSoldBy(
		UnsafeSupplier<String, Exception> soldByUnsafeSupplier) {

		try {
			soldBy = soldByUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String soldBy;

	public Status getStatus() {
		return status;
	}

	public String getStatusAsString() {
		if (status == null) {
			return null;
		}

		return status.toString();
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<Status, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Status status;

	public Tier getTier() {
		return tier;
	}

	public String getTierAsString() {
		if (tier == null) {
			return null;
		}

		return tier.toString();
	}

	public void setTier(Tier tier) {
		this.tier = tier;
	}

	public void setTier(UnsafeSupplier<Tier, Exception> tierUnsafeSupplier) {
		try {
			tier = tierUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Tier tier;

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setWebsite(
		UnsafeSupplier<String, Exception> websiteUnsafeSupplier) {

		try {
			website = websiteUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String website;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Account)) {
			return false;
		}

		Account account = (Account)object;

		return Objects.equals(toString(), account.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AccountSerDes.toJSON(this);
	}

}