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

package com.liferay.commerce.currency.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.currency.service.CommerceCurrencyServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CommerceCurrencyServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.currency.model.CommerceCurrencySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.currency.model.CommerceCurrency}, that is translated to a
 * {@link com.liferay.commerce.currency.model.CommerceCurrencySoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyServiceHttp
 * @see com.liferay.commerce.currency.model.CommerceCurrencySoap
 * @see CommerceCurrencyServiceUtil
 * @generated
 */
@ProviderType
public class CommerceCurrencyServiceSoap {
	public static com.liferay.commerce.currency.model.CommerceCurrencySoap addCommerceCurrency(
		String code, String[] nameMapLanguageIds, String[] nameMapValues,
		java.math.BigDecimal rate, String formatPattern, int maxFractionDigits,
		int minFractionDigits, String roundingMode, boolean primary,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);

			com.liferay.commerce.currency.model.CommerceCurrency returnValue = CommerceCurrencyServiceUtil.addCommerceCurrency(code,
					nameMap, rate, formatPattern, maxFractionDigits,
					minFractionDigits, roundingMode, primary, priority, active,
					serviceContext);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceCurrency(long commerceCurrencyId)
		throws RemoteException {
		try {
			CommerceCurrencyServiceUtil.deleteCommerceCurrency(commerceCurrencyId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap fetchPrimaryCommerceCurrency(
		long groupId) throws RemoteException {
		try {
			com.liferay.commerce.currency.model.CommerceCurrency returnValue = CommerceCurrencyServiceUtil.fetchPrimaryCommerceCurrency(groupId);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap[] getCommerceCurrencies(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.currency.model.CommerceCurrency> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.currency.model.CommerceCurrency> returnValue =
				CommerceCurrencyServiceUtil.getCommerceCurrencies(groupId,
					active, start, end, orderByComparator);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap[] getCommerceCurrencies(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.currency.model.CommerceCurrency> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.currency.model.CommerceCurrency> returnValue =
				CommerceCurrencyServiceUtil.getCommerceCurrencies(groupId,
					start, end, orderByComparator);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceCurrenciesCount(long groupId)
		throws RemoteException {
		try {
			int returnValue = CommerceCurrencyServiceUtil.getCommerceCurrenciesCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceCurrenciesCount(long groupId, boolean active)
		throws RemoteException {
		try {
			int returnValue = CommerceCurrencyServiceUtil.getCommerceCurrenciesCount(groupId,
					active);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap getCommerceCurrency(
		long commerceCurrencyId) throws RemoteException {
		try {
			com.liferay.commerce.currency.model.CommerceCurrency returnValue = CommerceCurrencyServiceUtil.getCommerceCurrency(commerceCurrencyId);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap getCommerceCurrency(
		long groupId, String code) throws RemoteException {
		try {
			com.liferay.commerce.currency.model.CommerceCurrency returnValue = CommerceCurrencyServiceUtil.getCommerceCurrency(groupId,
					code);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap setActive(
		long commerceCurrencyId, boolean active) throws RemoteException {
		try {
			com.liferay.commerce.currency.model.CommerceCurrency returnValue = CommerceCurrencyServiceUtil.setActive(commerceCurrencyId,
					active);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap setPrimary(
		long commerceCurrencyId, boolean primary) throws RemoteException {
		try {
			com.liferay.commerce.currency.model.CommerceCurrency returnValue = CommerceCurrencyServiceUtil.setPrimary(commerceCurrencyId,
					primary);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrencySoap updateCommerceCurrency(
		long commerceCurrencyId, String code, String[] nameMapLanguageIds,
		String[] nameMapValues, java.math.BigDecimal rate,
		String formatPattern, int maxFractionDigits, int minFractionDigits,
		String roundingMode, boolean primary, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);

			com.liferay.commerce.currency.model.CommerceCurrency returnValue = CommerceCurrencyServiceUtil.updateCommerceCurrency(commerceCurrencyId,
					code, nameMap, rate, formatPattern, maxFractionDigits,
					minFractionDigits, roundingMode, primary, priority, active,
					serviceContext);

			return com.liferay.commerce.currency.model.CommerceCurrencySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateExchangeRate(long commerceCurrencyId,
		String exchangeRateProviderKey) throws RemoteException {
		try {
			CommerceCurrencyServiceUtil.updateExchangeRate(commerceCurrencyId,
				exchangeRateProviderKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateExchangeRates(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			CommerceCurrencyServiceUtil.updateExchangeRates(serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceCurrencyServiceSoap.class);
}