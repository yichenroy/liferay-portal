/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.shipping.engine.fedex.internal.util;

import com.fedex.ws.rate.v22.Address;
import com.fedex.ws.rate.v22.ClientDetail;
import com.fedex.ws.rate.v22.Contact;
import com.fedex.ws.rate.v22.Dimensions;
import com.fedex.ws.rate.v22.DropoffType;
import com.fedex.ws.rate.v22.LinearUnits;
import com.fedex.ws.rate.v22.Money;
import com.fedex.ws.rate.v22.Notification;
import com.fedex.ws.rate.v22.NotificationSeverityType;
import com.fedex.ws.rate.v22.Party;
import com.fedex.ws.rate.v22.Payment;
import com.fedex.ws.rate.v22.PaymentType;
import com.fedex.ws.rate.v22.Payor;
import com.fedex.ws.rate.v22.RatePortType;
import com.fedex.ws.rate.v22.RateReply;
import com.fedex.ws.rate.v22.RateReplyDetail;
import com.fedex.ws.rate.v22.RateRequest;
import com.fedex.ws.rate.v22.RateRequestType;
import com.fedex.ws.rate.v22.RateServiceLocator;
import com.fedex.ws.rate.v22.RatedShipmentDetail;
import com.fedex.ws.rate.v22.RequestedPackageLineItem;
import com.fedex.ws.rate.v22.RequestedShipment;
import com.fedex.ws.rate.v22.ReturnedRateType;
import com.fedex.ws.rate.v22.ServiceType;
import com.fedex.ws.rate.v22.ShipmentRateDetail;
import com.fedex.ws.rate.v22.TransactionDetail;
import com.fedex.ws.rate.v22.VersionId;
import com.fedex.ws.rate.v22.WebAuthenticationCredential;
import com.fedex.ws.rate.v22.WebAuthenticationDetail;
import com.fedex.ws.rate.v22.Weight;
import com.fedex.ws.rate.v22.WeightUnits;

import com.liferay.commerce.configuration.CommerceShippingGroupServiceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.model.CommerceShippingOriginLocator;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.shipping.engine.fedex.internal.configuration.FedExCommerceShippingEngineGroupServiceConfiguration;
import com.liferay.commerce.shipping.engine.fedex.internal.constants.FedExCommerceShippingEngineConstants;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommerceShippingOriginLocatorRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.axis.types.NonNegativeInteger;
import org.apache.axis.types.PositiveInteger;

/**
 * @author Andrea Di Giorgi
 */
public class FedExCommerceShippingOptionHelper {

	public FedExCommerceShippingOptionHelper(
			CommerceCart commerceCart,
			CommerceCurrencyLocalService commerceCurrencyLocalService,
			CommercePriceCalculator commercePriceCalculator,
			CommerceShippingOriginLocatorRegistry
				commerceShippingOriginLocatorRegistry,
			CPInstanceLocalService cpInstanceLocalService,
			CPMeasurementUnitLocalService cpMeasurementUnitLocalService,
			ConfigurationProvider configurationProvider,
			ResourceBundle resourceBundle)
		throws Exception {

		_commerceCart = commerceCart;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commercePriceCalculator = commercePriceCalculator;
		_cpInstanceLocalService = cpInstanceLocalService;
		_cpMeasurementUnitLocalService = cpMeasurementUnitLocalService;
		_resourceBundle = resourceBundle;

		long groupId = _commerceCart.getGroupId();

		_commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(groupId);

		if (_commerceCurrency == null) {
			throw new CommerceShippingEngineException.MustSetPrimaryCurrency();
		}

		_dimensionCPMeasurementUnit = _getCPMeasurementUnit(
			CPConstants.MEASUREMENT_UNIT_TYPE_DIMENSION, LinearUnits._CM,
			LinearUnits._IN);
		_weightCPMeasurementUnit = _getCPMeasurementUnit(
			CPConstants.MEASUREMENT_UNIT_TYPE_WEIGHT, WeightUnits._KG,
			WeightUnits._LB);

		_shippingAddress = _commerceCart.getShippingAddress();

		if (_shippingAddress == null) {
			throw new CommerceShippingEngineException.MustSetShippingAddress();
		}

		CommerceShippingGroupServiceConfiguration
			commerceShippingGroupServiceConfiguration =
				configurationProvider.getConfiguration(
					CommerceShippingGroupServiceConfiguration.class,
					new GroupServiceSettingsLocator(
						groupId, CommerceConstants.SHIPPING_SERVICE_NAME));

		String commerceShippingOriginLocatorKey =
			commerceShippingGroupServiceConfiguration.
				commerceShippingOriginLocatorKey();

		_commerceShippingOriginLocator =
			commerceShippingOriginLocatorRegistry.
				getCommerceShippingOriginLocator(
					commerceShippingOriginLocatorKey);

		if (_commerceShippingOriginLocator == null) {
			throw new
				CommerceShippingEngineException.MustSetShippingOriginLocator(
					commerceShippingOriginLocatorKey);
		}

		_fedExCommerceShippingEngineGroupServiceConfiguration =
			configurationProvider.getConfiguration(
				FedExCommerceShippingEngineGroupServiceConfiguration.class,
				new GroupServiceSettingsLocator(
					groupId,
					FedExCommerceShippingEngineConstants.SERVICE_NAME));

		_serviceTypes = SetUtil.fromArray(
			StringUtil.split(
				_fedExCommerceShippingEngineGroupServiceConfiguration.
					serviceTypes()));
	}

	public List<CommerceShippingOption> getCommerceShippingOptions()
		throws Exception {

		Map<String, List<Double>> rates = new HashMap<>();

		Map<CommerceAddress, List<CommerceCartItem>> originAddresses =
			_commerceShippingOriginLocator.getOriginAddresses(_commerceCart);

		for (Map.Entry<CommerceAddress, List<CommerceCartItem>> entry :
				originAddresses.entrySet()) {

			_executeRateRequest(rates, entry.getValue(), entry.getKey());
		}

		List<CommerceShippingOption> commerceShippingOptions = new ArrayList<>(
			_serviceTypes.size());

		for (Map.Entry<String, List<Double>> entry : rates.entrySet()) {
			String name = entry.getKey();
			List<Double> amounts = entry.getValue();

			if (amounts.size() < originAddresses.size()) {
				continue;
			}

			String label = ResourceBundleUtil.getString(
				_resourceBundle,
				FedExCommerceShippingEngineConstants.getServiceTypeLabel(name));
			double amount = MathUtil.sum(amounts.toArray(new Double[0]));

			commerceShippingOptions.add(
				new CommerceShippingOption(name, label, amount));
		}

		return commerceShippingOptions;
	}

	private void _executeRateRequest(
			Map<String, List<Double>> rates,
			List<CommerceCartItem> commerceCartItems,
			CommerceAddress originAddress)
		throws Exception {

		RateRequest rateRequest = _getRateRequest(
			commerceCartItems, originAddress);

		RateServiceLocator rateServiceLocator = new RateServiceLocator();

		String url =
			_fedExCommerceShippingEngineGroupServiceConfiguration.url();

		if (Validator.isNotNull(url)) {
			rateServiceLocator.setRateServicePortEndpointAddress(url);
		}

		RatePortType ratePortType = rateServiceLocator.getRateServicePort();

		RateReply rateReply = ratePortType.getRates(rateRequest);

		NotificationSeverityType notificationSeverityType =
			rateReply.getHighestSeverity();
		RateReplyDetail[] rateReplyDetails = rateReply.getRateReplyDetails();

		if (ArrayUtil.isEmpty(rateReplyDetails) ||
			(!notificationSeverityType.equals(NotificationSeverityType.NOTE) &&
				!notificationSeverityType.equals(
					NotificationSeverityType.SUCCESS) &&
				!notificationSeverityType.equals(
					NotificationSeverityType.WARNING))) {

			throw new CommerceShippingEngineException.ServerError(
				_getErrorKVPs(rateReply.getNotifications()));
		}

		boolean useDiscountedRates =
			_fedExCommerceShippingEngineGroupServiceConfiguration.
				useDiscountedRates();

		for (RateReplyDetail rateReplyDetail : rateReplyDetails) {
			ServiceType serviceType = rateReplyDetail.getServiceType();

			String name = serviceType.getValue();

			if (!_serviceTypes.contains(name)) {
				continue;
			}

			double amount = 0;

			for (RatedShipmentDetail ratedShipmentDetail :
					rateReplyDetail.getRatedShipmentDetails()) {

				ShipmentRateDetail shipmentRateDetail =
					ratedShipmentDetail.getShipmentRateDetail();

				ReturnedRateType returnedRateType =
					shipmentRateDetail.getRateType();

				if ((useDiscountedRates &&
					 (returnedRateType.equals(
						 ReturnedRateType.PAYOR_ACCOUNT_PACKAGE) ||
					returnedRateType.equals(
						ReturnedRateType.PAYOR_ACCOUNT_SHIPMENT))) ||
					returnedRateType.equals(
						ReturnedRateType.PAYOR_LIST_PACKAGE) ||
					returnedRateType.equals(
						ReturnedRateType.PAYOR_LIST_SHIPMENT)) {

					amount = _getAmount(shipmentRateDetail.getTotalNetCharge());
				}
			}

			List<Double> amounts = rates.get(name);

			if (amounts == null) {
				amounts = new ArrayList<>();

				rates.put(name, amounts);
			}

			amounts.add(amount);
		}
	}

	private Address _getAddress(CommerceAddress commerceAddress)
		throws PortalException {

		Address address = new Address();

		address.setCity(commerceAddress.getCity());

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		address.setCountryCode(commerceCountry.getTwoLettersISOCode());
		address.setCountryName(commerceCountry.getName(LocaleUtil.US));

		address.setPostalCode(commerceAddress.getZip());

		if (_fedExCommerceShippingEngineGroupServiceConfiguration.
				useResidentialRates()) {

			address.setResidential(Boolean.TRUE);
		}

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		if (commerceRegion != null) {
			address.setStateOrProvinceCode(commerceRegion.getCode());
		}

		List<String> streetLines = new ArrayList<>(3);

		streetLines.add(commerceAddress.getStreet1());

		String street2 = commerceAddress.getStreet2();

		if (Validator.isNotNull(street2)) {
			streetLines.add(street2);
		}

		String street3 = commerceAddress.getStreet3();

		if (Validator.isNotNull(street3)) {
			streetLines.add(street3);
		}

		address.setStreetLines(streetLines.toArray(new String[0]));

		return address;
	}

	private double _getAmount(List<CommerceCartItem> commerceCartItems)
		throws Exception {

		double amount = 0;

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			amount += _commercePriceCalculator.getPrice(commerceCartItem);
		}

		return amount;
	}

	private double _getAmount(Money money)
		throws CommerceShippingEngineException {

		CommerceCurrency moneyCommerceCurrency = null;

		String code = money.getCurrency();

		List<CommerceCurrency> commerceCurrencies =
			_commerceCurrencyLocalService.getCommerceCurrencies(
				_commerceCart.getGroupId(), true);

		for (CommerceCurrency commerceCurrency : commerceCurrencies) {
			if (StringUtil.equalsIgnoreCase(
					code, commerceCurrency.getCode(LocaleUtil.US))) {

				moneyCommerceCurrency = commerceCurrency;

				break;
			}
		}

		if (moneyCommerceCurrency == null) {
			throw new CommerceShippingEngineException.MustSetCurrency(code);
		}

		BigDecimal amount = money.getAmount();

		return amount.doubleValue() * moneyCommerceCurrency.getRate();
	}

	private ClientDetail _getClientDetail() {
		ClientDetail clientDetail = new ClientDetail();

		clientDetail.setAccountNumber(
			_fedExCommerceShippingEngineGroupServiceConfiguration.
				accountNumber());
		clientDetail.setMeterNumber(
			_fedExCommerceShippingEngineGroupServiceConfiguration.
				meterNumber());

		return clientDetail;
	}

	private Contact _getContact(CommerceAddress commerceAddress) {
		Contact contact = new Contact();

		contact.setPersonName(commerceAddress.getName());
		contact.setPhoneNumber(commerceAddress.getPhoneNumber());

		return contact;
	}

	private CPMeasurementUnit _getCPMeasurementUnit(int type, String... keys)
		throws CommerceShippingEngineException {

		List<CPMeasurementUnit> cpMeasurementUnits =
			_cpMeasurementUnitLocalService.getCPMeasurementUnits(
				_commerceCart.getGroupId(), keys, type);

		if (cpMeasurementUnits.isEmpty()) {
			throw new CommerceShippingEngineException.MustSetMeasurementUnit(
				keys);
		}

		for (CPMeasurementUnit cpMeasurementUnit : cpMeasurementUnits) {
			if (cpMeasurementUnit.isPrimary()) {
				return cpMeasurementUnit;
			}
		}

		return cpMeasurementUnits.get(0);
	}

	private List<KeyValuePair> _getErrorKVPs(Notification[] notifications) {
		List<KeyValuePair> errorKVPs = new ArrayList<>(notifications.length);

		for (Notification notification : notifications) {
			errorKVPs.add(
				new KeyValuePair(
					notification.getCode(), notification.getMessage()));
		}

		return errorKVPs;
	}

	private int _getFedExDimension(double dimension) {
		int convertedDimension = (int)Math.ceil(
			dimension * _dimensionCPMeasurementUnit.getRate());

		if (convertedDimension < 1) {
			convertedDimension = 1;
		}

		return convertedDimension;
	}

	private BigDecimal _getFedExWeight(double weight) {
		double convertedweight = weight * _weightCPMeasurementUnit.getRate();

		if (convertedweight < 1) {
			convertedweight = 1;
		}

		return BigDecimal.valueOf(convertedweight);
	}

	private Money _getMoney(double amount) {
		return new Money(
			_commerceCurrency.getCode(LocaleUtil.US),
			BigDecimal.valueOf(amount));
	}

	private NonNegativeInteger _getNonNegativeInteger(int d) {
		return new NonNegativeInteger(String.valueOf(d));
	}

	private Party _getParty(CommerceAddress commerceAddress)
		throws PortalException {

		Party party = new Party();

		party.setAddress(_getAddress(commerceAddress));
		party.setContact(_getContact(commerceAddress));

		return party;
	}

	private Payment _getPayment(String accountNumber) {
		Party party = new Party();

		party.setAccountNumber(accountNumber);

		Payor payor = new Payor(party);

		return new Payment(PaymentType.SENDER, payor);
	}

	private PositiveInteger _getPositiveInteger(int i) {
		return new PositiveInteger(String.valueOf(i));
	}

	private RateRequest _getRateRequest(
			List<CommerceCartItem> commerceCartItems,
			CommerceAddress originAddress)
		throws Exception {

		RateRequest rateRequest = new RateRequest();

		rateRequest.setClientDetail(_getClientDetail());
		rateRequest.setRequestedShipment(
			_getRequestedShipment(commerceCartItems, originAddress));
		rateRequest.setReturnTransitAndCommit(Boolean.TRUE);
		rateRequest.setTransactionDetail(
			_getTransactionDetail(commerceCartItems));
		rateRequest.setVersion(new VersionId("crs", 22, 0, 0));
		rateRequest.setWebAuthenticationDetail(_getWebAuthenticationDetail());

		return rateRequest;
	}

	private RequestedPackageLineItem[]
			_getRequestedPackageLineItemsOneItemPerPackage(
				List<CommerceCartItem> commerceCartItems)
		throws Exception {

		List<RequestedPackageLineItem> requestedPackageLineItems =
			new ArrayList<>(commerceCartItems.size());

		LinearUnits linearUnits = LinearUnits.fromValue(
			StringUtil.toUpperCase(_dimensionCPMeasurementUnit.getKey()));
		WeightUnits weightUnits = WeightUnits.fromValue(
			StringUtil.toUpperCase(_weightCPMeasurementUnit.getKey()));

		for (int i = 0; i < commerceCartItems.size(); i++) {
			CommerceCartItem commerceCartItem = commerceCartItems.get(i);

			CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
				commerceCartItem.getCPInstanceId());

			int width = _getFedExDimension(cpInstance.getWidth());
			int height = _getFedExDimension(cpInstance.getHeight());
			int depth = _getFedExDimension(cpInstance.getDepth());

			BigDecimal weight = _getFedExWeight(cpInstance.getWeight());

			double price = _commercePriceCalculator.getPrice(
				cpInstance.getCPInstanceId(), 1);

			for (int j = 0; j < commerceCartItem.getQuantity(); j++) {
				RequestedPackageLineItem requestedPackageLineItem =
					new RequestedPackageLineItem();

				requestedPackageLineItem.setDimensions(
					new Dimensions(
						_getNonNegativeInteger(depth),
						_getNonNegativeInteger(width),
						_getNonNegativeInteger(height), linearUnits));
				requestedPackageLineItem.setGroupPackageCount(
					_getNonNegativeInteger(1));
				requestedPackageLineItem.setInsuredValue(_getMoney(price));
				requestedPackageLineItem.setSequenceNumber(
					_getPositiveInteger(i + 1));
				requestedPackageLineItem.setWeight(
					new Weight(weightUnits, weight));

				requestedPackageLineItems.add(requestedPackageLineItem);
			}
		}

		return requestedPackageLineItems.toArray(
			new RequestedPackageLineItem[0]);
	}

	private RequestedShipment _getRequestedShipment(
			List<CommerceCartItem> commerceCartItems,
			CommerceAddress originAddress)
		throws Exception {

		RequestedShipment requestedShipment = new RequestedShipment();

		RequestedPackageLineItem[] requestedPackageLineItems = null;

		String packingType =
			_fedExCommerceShippingEngineGroupServiceConfiguration.packingType();

		if (packingType.equals(
				FedExCommerceShippingEngineConstants.
					PACKING_TYPE_BY_DIMENSIONS)) {
		}
		else if (packingType.equals(
					FedExCommerceShippingEngineConstants.
						PACKING_TYPE_BY_VOLUME)) {
		}
		else if (packingType.equals(
					FedExCommerceShippingEngineConstants.
						PACKING_TYPE_ONE_ITEM_PER_PACKAGE)) {

			requestedPackageLineItems =
				_getRequestedPackageLineItemsOneItemPerPackage(
					commerceCartItems);
		}

		requestedShipment.setDropoffType(
			DropoffType.fromValue(
				_fedExCommerceShippingEngineGroupServiceConfiguration.
					dropoffType()));
		requestedShipment.setPackageCount(
			_getNonNegativeInteger(requestedPackageLineItems.length));
		requestedShipment.setRateRequestTypes(
			new RateRequestType[] {
				RateRequestType.LIST, RateRequestType.PREFERRED
			});
		requestedShipment.setRecipient(_getParty(_shippingAddress));
		requestedShipment.setRecipientLocationNumber(
			String.valueOf(_shippingAddress.getCommerceAddressId()));
		requestedShipment.setRequestedPackageLineItems(
			requestedPackageLineItems);
		requestedShipment.setShipper(_getParty(originAddress));
		requestedShipment.setShippingChargesPayment(
			_getPayment(
				_fedExCommerceShippingEngineGroupServiceConfiguration.
					accountNumber()));
		requestedShipment.setShipTimestamp(_getShipTimestamp());
		requestedShipment.setTotalInsuredValue(
			_getMoney(_getAmount(commerceCartItems)));

		return requestedShipment;
	}

	private Calendar _getShipTimestamp() {
		Calendar calendar = CalendarFactoryUtil.getCalendar();

		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			calendar.add(Calendar.DATE, 2);
		}

		return calendar;
	}

	private TransactionDetail _getTransactionDetail(
		List<CommerceCartItem> commerceCartItems) {

		TransactionDetail transactionDetail = new TransactionDetail();

		StringBundler sb = new StringBundler(commerceCartItems.size() * 2);

		sb.append("Liferay Commerce rate request for cart items ");

		boolean first = true;

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			if (!first) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			first = false;

			sb.append(commerceCartItem.getCommerceCartItemId());
		}

		transactionDetail.setCustomerTransactionId(sb.toString());

		return transactionDetail;
	}

	private WebAuthenticationDetail _getWebAuthenticationDetail() {
		WebAuthenticationCredential webAuthenticationCredential =
			new WebAuthenticationCredential(
				_fedExCommerceShippingEngineGroupServiceConfiguration.key(),
				_fedExCommerceShippingEngineGroupServiceConfiguration.
					password());

		return new WebAuthenticationDetail(null, webAuthenticationCredential);
	}

	private final CommerceCart _commerceCart;
	private final CommerceCurrency _commerceCurrency;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final CommercePriceCalculator _commercePriceCalculator;
	private final CommerceShippingOriginLocator _commerceShippingOriginLocator;
	private final CPInstanceLocalService _cpInstanceLocalService;
	private final CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;
	private final CPMeasurementUnit _dimensionCPMeasurementUnit;
	private final FedExCommerceShippingEngineGroupServiceConfiguration
		_fedExCommerceShippingEngineGroupServiceConfiguration;
	private final ResourceBundle _resourceBundle;
	private final Set<String> _serviceTypes;
	private final CommerceAddress _shippingAddress;
	private final CPMeasurementUnit _weightCPMeasurementUnit;

}