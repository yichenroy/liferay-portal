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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceAddressRestrictionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceAddressRestrictionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommerceAddressRestrictionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommerceAddressRestriction}, that is translated to a
 * {@link com.liferay.commerce.model.CommerceAddressRestrictionSoap}. Methods that SOAP cannot
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
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionServiceHttp
 * @see com.liferay.commerce.model.CommerceAddressRestrictionSoap
 * @see CommerceAddressRestrictionServiceUtil
 * @generated
 */
@ProviderType
public class CommerceAddressRestrictionServiceSoap {
	public static com.liferay.commerce.model.CommerceAddressRestrictionSoap addCommerceAddressRestriction(
		String className, long classPK, long commerceCountryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceAddressRestriction returnValue = CommerceAddressRestrictionServiceUtil.addCommerceAddressRestriction(className,
					classPK, commerceCountryId, serviceContext);

			return com.liferay.commerce.model.CommerceAddressRestrictionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceAddressRestriction(
		long commerceAddressRestrictionId) throws RemoteException {
		try {
			CommerceAddressRestrictionServiceUtil.deleteCommerceAddressRestriction(commerceAddressRestrictionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressRestrictionSoap fetchCommerceAddressRestriction(
		String className, long classPK, long commerceCountryId)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceAddressRestriction returnValue = CommerceAddressRestrictionServiceUtil.fetchCommerceAddressRestriction(className,
					classPK, commerceCountryId);

			return com.liferay.commerce.model.CommerceAddressRestrictionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressRestrictionSoap[] getCommerceAddressRestrictions(
		String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAddressRestriction> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceAddressRestriction> returnValue =
				CommerceAddressRestrictionServiceUtil.getCommerceAddressRestrictions(className,
					classPK, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceAddressRestrictionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceAddressRestrictionsCount(String className,
		long classPK) throws RemoteException {
		try {
			int returnValue = CommerceAddressRestrictionServiceUtil.getCommerceAddressRestrictionsCount(className,
					classPK);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean isCommerceAddressRestricted(String className,
		long classPK, long commerceCountryId) throws RemoteException {
		try {
			boolean returnValue = CommerceAddressRestrictionServiceUtil.isCommerceAddressRestricted(className,
					classPK, commerceCountryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean isCommercePaymentMethodRestricted(
		long commercePaymentMethodId, long commerceCountryId)
		throws RemoteException {
		try {
			boolean returnValue = CommerceAddressRestrictionServiceUtil.isCommercePaymentMethodRestricted(commercePaymentMethodId,
					commerceCountryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static boolean isCommerceShippingMethodRestricted(
		long commerceShippingMethodId, long commerceCountryId)
		throws RemoteException {
		try {
			boolean returnValue = CommerceAddressRestrictionServiceUtil.isCommerceShippingMethodRestricted(commerceShippingMethodId,
					commerceCountryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceAddressRestrictionServiceSoap.class);
}