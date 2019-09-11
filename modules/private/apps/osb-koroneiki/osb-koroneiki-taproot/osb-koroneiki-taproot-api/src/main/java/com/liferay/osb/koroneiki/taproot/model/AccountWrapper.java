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

package com.liferay.osb.koroneiki.taproot.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Account}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Account
 * @generated
 */
public class AccountWrapper
	extends BaseModelWrapper<Account>
	implements Account, ModelWrapper<Account> {

	public AccountWrapper(Account account) {
		super(account);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("accountId", getAccountId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("accountKey", getAccountKey());
		attributes.put("parentAccountId", getParentAccountId());
		attributes.put("name", getName());
		attributes.put("code", getCode());
		attributes.put("description", getDescription());
		attributes.put("notes", getNotes());
		attributes.put("logoId", getLogoId());
		attributes.put("contactEmailAddress", getContactEmailAddress());
		attributes.put("profileEmailAddress", getProfileEmailAddress());
		attributes.put("phoneNumber", getPhoneNumber());
		attributes.put("faxNumber", getFaxNumber());
		attributes.put("website", getWebsite());
		attributes.put("industry", getIndustry());
		attributes.put("tier", getTier());
		attributes.put("soldBy", getSoldBy());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("statusMessage", getStatusMessage());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String accountKey = (String)attributes.get("accountKey");

		if (accountKey != null) {
			setAccountKey(accountKey);
		}

		Long parentAccountId = (Long)attributes.get("parentAccountId");

		if (parentAccountId != null) {
			setParentAccountId(parentAccountId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String code = (String)attributes.get("code");

		if (code != null) {
			setCode(code);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String notes = (String)attributes.get("notes");

		if (notes != null) {
			setNotes(notes);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}

		String contactEmailAddress = (String)attributes.get(
			"contactEmailAddress");

		if (contactEmailAddress != null) {
			setContactEmailAddress(contactEmailAddress);
		}

		String profileEmailAddress = (String)attributes.get(
			"profileEmailAddress");

		if (profileEmailAddress != null) {
			setProfileEmailAddress(profileEmailAddress);
		}

		String phoneNumber = (String)attributes.get("phoneNumber");

		if (phoneNumber != null) {
			setPhoneNumber(phoneNumber);
		}

		String faxNumber = (String)attributes.get("faxNumber");

		if (faxNumber != null) {
			setFaxNumber(faxNumber);
		}

		String website = (String)attributes.get("website");

		if (website != null) {
			setWebsite(website);
		}

		String industry = (String)attributes.get("industry");

		if (industry != null) {
			setIndustry(industry);
		}

		String tier = (String)attributes.get("tier");

		if (tier != null) {
			setTier(tier);
		}

		String soldBy = (String)attributes.get("soldBy");

		if (soldBy != null) {
			setSoldBy(soldBy);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}

		String statusMessage = (String)attributes.get("statusMessage");

		if (statusMessage != null) {
			setStatusMessage(statusMessage);
		}
	}

	/**
	 * Returns the account ID of this account.
	 *
	 * @return the account ID of this account
	 */
	@Override
	public long getAccountId() {
		return model.getAccountId();
	}

	/**
	 * Returns the account key of this account.
	 *
	 * @return the account key of this account
	 */
	@Override
	public String getAccountKey() {
		return model.getAccountKey();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Address>
		getAddresses() {

		return model.getAddresses();
	}

	@Override
	public java.util.List<Account> getChildAccounts()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getChildAccounts();
	}

	/**
	 * Returns the code of this account.
	 *
	 * @return the code of this account
	 */
	@Override
	public String getCode() {
		return model.getCode();
	}

	/**
	 * Returns the company ID of this account.
	 *
	 * @return the company ID of this account
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the contact email address of this account.
	 *
	 * @return the contact email address of this account
	 */
	@Override
	public String getContactEmailAddress() {
		return model.getContactEmailAddress();
	}

	/**
	 * Returns the create date of this account.
	 *
	 * @return the create date of this account
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this account.
	 *
	 * @return the description of this account
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.root.model.ExternalLink>
		getExternalLinks() {

		return model.getExternalLinks();
	}

	/**
	 * Returns the fax number of this account.
	 *
	 * @return the fax number of this account
	 */
	@Override
	public String getFaxNumber() {
		return model.getFaxNumber();
	}

	/**
	 * Returns the industry of this account.
	 *
	 * @return the industry of this account
	 */
	@Override
	public String getIndustry() {
		return model.getIndustry();
	}

	/**
	 * Returns the logo ID of this account.
	 *
	 * @return the logo ID of this account
	 */
	@Override
	public long getLogoId() {
		return model.getLogoId();
	}

	/**
	 * Returns the modified date of this account.
	 *
	 * @return the modified date of this account
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this account.
	 *
	 * @return the name of this account
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the notes of this account.
	 *
	 * @return the notes of this account
	 */
	@Override
	public String getNotes() {
		return model.getNotes();
	}

	@Override
	public Account getParentAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentAccount();
	}

	/**
	 * Returns the parent account ID of this account.
	 *
	 * @return the parent account ID of this account
	 */
	@Override
	public long getParentAccountId() {
		return model.getParentAccountId();
	}

	/**
	 * Returns the phone number of this account.
	 *
	 * @return the phone number of this account
	 */
	@Override
	public String getPhoneNumber() {
		return model.getPhoneNumber();
	}

	/**
	 * Returns the primary key of this account.
	 *
	 * @return the primary key of this account
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the profile email address of this account.
	 *
	 * @return the profile email address of this account
	 */
	@Override
	public String getProfileEmailAddress() {
		return model.getProfileEmailAddress();
	}

	/**
	 * Returns the sold by of this account.
	 *
	 * @return the sold by of this account
	 */
	@Override
	public String getSoldBy() {
		return model.getSoldBy();
	}

	/**
	 * Returns the status of this account.
	 *
	 * @return the status of this account
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this account.
	 *
	 * @return the status by user ID of this account
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this account.
	 *
	 * @return the status by user name of this account
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this account.
	 *
	 * @return the status by user uuid of this account
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this account.
	 *
	 * @return the status date of this account
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	@Override
	public String getStatusLabel() {
		return model.getStatusLabel();
	}

	/**
	 * Returns the status message of this account.
	 *
	 * @return the status message of this account
	 */
	@Override
	public String getStatusMessage() {
		return model.getStatusMessage();
	}

	/**
	 * Returns the tier of this account.
	 *
	 * @return the tier of this account
	 */
	@Override
	public String getTier() {
		return model.getTier();
	}

	/**
	 * Returns the user ID of this account.
	 *
	 * @return the user ID of this account
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this account.
	 *
	 * @return the user uuid of this account
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this account.
	 *
	 * @return the uuid of this account
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the website of this account.
	 *
	 * @return the website of this account
	 */
	@Override
	public String getWebsite() {
		return model.getWebsite();
	}

	/**
	 * Returns <code>true</code> if this account is approved.
	 *
	 * @return <code>true</code> if this account is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this account is denied.
	 *
	 * @return <code>true</code> if this account is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this account is a draft.
	 *
	 * @return <code>true</code> if this account is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this account is expired.
	 *
	 * @return <code>true</code> if this account is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this account is inactive.
	 *
	 * @return <code>true</code> if this account is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this account is incomplete.
	 *
	 * @return <code>true</code> if this account is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this account is pending.
	 *
	 * @return <code>true</code> if this account is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this account is scheduled.
	 *
	 * @return <code>true</code> if this account is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a account model instance should use the <code>Account</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account ID of this account.
	 *
	 * @param accountId the account ID of this account
	 */
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	 * Sets the account key of this account.
	 *
	 * @param accountKey the account key of this account
	 */
	@Override
	public void setAccountKey(String accountKey) {
		model.setAccountKey(accountKey);
	}

	/**
	 * Sets the code of this account.
	 *
	 * @param code the code of this account
	 */
	@Override
	public void setCode(String code) {
		model.setCode(code);
	}

	/**
	 * Sets the company ID of this account.
	 *
	 * @param companyId the company ID of this account
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the contact email address of this account.
	 *
	 * @param contactEmailAddress the contact email address of this account
	 */
	@Override
	public void setContactEmailAddress(String contactEmailAddress) {
		model.setContactEmailAddress(contactEmailAddress);
	}

	/**
	 * Sets the create date of this account.
	 *
	 * @param createDate the create date of this account
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this account.
	 *
	 * @param description the description of this account
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the fax number of this account.
	 *
	 * @param faxNumber the fax number of this account
	 */
	@Override
	public void setFaxNumber(String faxNumber) {
		model.setFaxNumber(faxNumber);
	}

	/**
	 * Sets the industry of this account.
	 *
	 * @param industry the industry of this account
	 */
	@Override
	public void setIndustry(String industry) {
		model.setIndustry(industry);
	}

	/**
	 * Sets the logo ID of this account.
	 *
	 * @param logoId the logo ID of this account
	 */
	@Override
	public void setLogoId(long logoId) {
		model.setLogoId(logoId);
	}

	/**
	 * Sets the modified date of this account.
	 *
	 * @param modifiedDate the modified date of this account
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this account.
	 *
	 * @param name the name of this account
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the notes of this account.
	 *
	 * @param notes the notes of this account
	 */
	@Override
	public void setNotes(String notes) {
		model.setNotes(notes);
	}

	/**
	 * Sets the parent account ID of this account.
	 *
	 * @param parentAccountId the parent account ID of this account
	 */
	@Override
	public void setParentAccountId(long parentAccountId) {
		model.setParentAccountId(parentAccountId);
	}

	/**
	 * Sets the phone number of this account.
	 *
	 * @param phoneNumber the phone number of this account
	 */
	@Override
	public void setPhoneNumber(String phoneNumber) {
		model.setPhoneNumber(phoneNumber);
	}

	/**
	 * Sets the primary key of this account.
	 *
	 * @param primaryKey the primary key of this account
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the profile email address of this account.
	 *
	 * @param profileEmailAddress the profile email address of this account
	 */
	@Override
	public void setProfileEmailAddress(String profileEmailAddress) {
		model.setProfileEmailAddress(profileEmailAddress);
	}

	/**
	 * Sets the sold by of this account.
	 *
	 * @param soldBy the sold by of this account
	 */
	@Override
	public void setSoldBy(String soldBy) {
		model.setSoldBy(soldBy);
	}

	/**
	 * Sets the status of this account.
	 *
	 * @param status the status of this account
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this account.
	 *
	 * @param statusByUserId the status by user ID of this account
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this account.
	 *
	 * @param statusByUserName the status by user name of this account
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this account.
	 *
	 * @param statusByUserUuid the status by user uuid of this account
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this account.
	 *
	 * @param statusDate the status date of this account
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the status message of this account.
	 *
	 * @param statusMessage the status message of this account
	 */
	@Override
	public void setStatusMessage(String statusMessage) {
		model.setStatusMessage(statusMessage);
	}

	/**
	 * Sets the tier of this account.
	 *
	 * @param tier the tier of this account
	 */
	@Override
	public void setTier(String tier) {
		model.setTier(tier);
	}

	/**
	 * Sets the user ID of this account.
	 *
	 * @param userId the user ID of this account
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this account.
	 *
	 * @param userUuid the user uuid of this account
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this account.
	 *
	 * @param uuid the uuid of this account
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the website of this account.
	 *
	 * @param website the website of this account
	 */
	@Override
	public void setWebsite(String website) {
		model.setWebsite(website);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected AccountWrapper wrap(Account account) {
		return new AccountWrapper(account);
	}

}