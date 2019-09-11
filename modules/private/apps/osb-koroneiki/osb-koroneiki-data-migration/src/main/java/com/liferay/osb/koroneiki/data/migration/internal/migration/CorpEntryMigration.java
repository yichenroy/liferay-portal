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

package com.liferay.osb.koroneiki.data.migration.internal.migration;

import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalService;
import com.liferay.osb.koroneiki.root.util.ModelKeyGenerator;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = CorpEntryMigration.class)
public class CorpEntryMigration {

	public void migrate(long userId) throws Exception {
		User user = _userLocalService.getUser(userId);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from OSB_CorpEntry");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Account account = _accountLocalService.createAccount(
					resultSet.getLong("corpEntryId"));

				account.setCompanyId(user.getCompanyId());
				account.setUserId(userId);
				account.setCreateDate(resultSet.getTimestamp("createDate"));
				account.setModifiedDate(resultSet.getTimestamp("modifiedDate"));
				account.setAccountKey(
					ModelKeyGenerator.generate(account.getAccountId()));
				account.setName(resultSet.getString("name"));

				Map<Locale, String> descriptionMap =
					LocalizationUtil.getLocalizationMap(
						resultSet.getString("description"));

				account.setDescription(descriptionMap.get(LocaleUtil.US));

				account.setLogoId(resultSet.getLong("logoId"));
				account.setContactEmailAddress(
					resultSet.getString("contactEmailAddress"));
				account.setProfileEmailAddress(
					resultSet.getString("profileEmailAddress"));
				account.setPhoneNumber(resultSet.getString("phoneNumber"));
				account.setFaxNumber(resultSet.getString("faxNumber"));
				account.setWebsite(resultSet.getString("website"));
				account.setStatus(resultSet.getInt("status"));
				account.setStatusByUserId(userId);
				account.setStatusByUserName(user.getFullName());
				account.setStatusDate(new Date());
				account.setStatusMessage(StringPool.BLANK);

				_accountLocalService.addAccount(account);

				_externalLinkLocalService.addExternalLink(
					userId, Account.class.getName(), account.getAccountId(),
					"dossiera", "account",
					resultSet.getString("dossieraAccountKey"));

				_migrateAddress(
					connection, resultSet.getLong("addressId"), userId,
					account.getAccountId());

				if (_log.isInfoEnabled()) {
					_log.info("Migrated CorpEntry " + account.getAccountId());
				}
			}
		}
	}

	private void _migrateAddress(
			Connection connection, long addressId, long userId, long accountId)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("select WEB_Address.*, WEB_Country.A2, WEB_Region.name as ");
		sb.append("regionName from WEB_Address left join WEB_Country on ");
		sb.append("WEB_Country.countryId = WEB_Address.countryId left join ");
		sb.append("WEB_Region on WEB_Region.regionId = WEB_Address.regionId ");
		sb.append("where WEB_Address.addressId = ");
		sb.append(addressId);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				String countryA2 = resultSet.getString("A2");
				String regionName = resultSet.getString("regionName");

				long regionId = 0;
				long countryId = 0;

				if (Validator.isNotNull(countryA2)) {
					Country country = _countryService.getCountryByA2(countryA2);

					countryId = country.getCountryId();

					if (Validator.isNotNull(regionName)) {
						List<Region> regions = _regionService.getRegions(
							countryId);

						for (Region region : regions) {
							if (regionName.equals(region.getName())) {
								regionId = region.getRegionId();

								break;
							}
						}
					}
				}

				_addressLocalService.addAddress(
					userId, Account.class.getName(), accountId,
					resultSet.getString("street1"),
					resultSet.getString("street2"),
					resultSet.getString("street3"), resultSet.getString("city"),
					resultSet.getString("zip"), regionId, countryId, 0,
					resultSet.getBoolean("mailing"),
					resultSet.getBoolean("primary_"), new ServiceContext());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CorpEntryMigration.class);

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private CountryService _countryService;

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

	@Reference
	private RegionService _regionService;

	@Reference
	private UserLocalService _userLocalService;

}