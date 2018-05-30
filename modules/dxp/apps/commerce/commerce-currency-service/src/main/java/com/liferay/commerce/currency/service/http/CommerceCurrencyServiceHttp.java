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
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceCurrencyServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyServiceSoap
 * @see HttpPrincipal
 * @see CommerceCurrencyServiceUtil
 * @generated
 */
@ProviderType
public class CommerceCurrencyServiceHttp {
	public static com.liferay.commerce.currency.model.CommerceCurrency addCommerceCurrency(
		HttpPrincipal httpPrincipal, String code,
		java.util.Map<java.util.Locale, String> nameMap,
		java.math.BigDecimal rate, String formatPattern, int maxFractionDigits,
		int minFractionDigits, String roundingMode, boolean primary,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"addCommerceCurrency", _addCommerceCurrencyParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, code,
					nameMap, rate, formatPattern, maxFractionDigits,
					minFractionDigits, roundingMode, primary, priority, active,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.currency.model.CommerceCurrency)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceCurrency(HttpPrincipal httpPrincipal,
		long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"deleteCommerceCurrency",
					_deleteCommerceCurrencyParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCurrencyId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency fetchPrimaryCommerceCurrency(
		HttpPrincipal httpPrincipal, long groupId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"fetchPrimaryCommerceCurrency",
					_fetchPrimaryCommerceCurrencyParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.currency.model.CommerceCurrency)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.currency.model.CommerceCurrency> getCommerceCurrencies(
		HttpPrincipal httpPrincipal, long groupId, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.currency.model.CommerceCurrency> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"getCommerceCurrencies",
					_getCommerceCurrenciesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.currency.model.CommerceCurrency>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.currency.model.CommerceCurrency> getCommerceCurrencies(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.currency.model.CommerceCurrency> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"getCommerceCurrencies",
					_getCommerceCurrenciesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.currency.model.CommerceCurrency>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceCurrenciesCount(HttpPrincipal httpPrincipal,
		long groupId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"getCommerceCurrenciesCount",
					_getCommerceCurrenciesCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceCurrenciesCount(HttpPrincipal httpPrincipal,
		long groupId, boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"getCommerceCurrenciesCount",
					_getCommerceCurrenciesCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency getCommerceCurrency(
		HttpPrincipal httpPrincipal, long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"getCommerceCurrency", _getCommerceCurrencyParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCurrencyId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.currency.model.CommerceCurrency)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency getCommerceCurrency(
		HttpPrincipal httpPrincipal, long groupId, String code)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"getCommerceCurrency", _getCommerceCurrencyParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					code);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.currency.model.CommerceCurrency)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency setActive(
		HttpPrincipal httpPrincipal, long commerceCurrencyId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"setActive", _setActiveParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCurrencyId, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.currency.model.CommerceCurrency)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency setPrimary(
		HttpPrincipal httpPrincipal, long commerceCurrencyId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"setPrimary", _setPrimaryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCurrencyId, primary);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.currency.model.CommerceCurrency)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.currency.model.CommerceCurrency updateCommerceCurrency(
		HttpPrincipal httpPrincipal, long commerceCurrencyId, String code,
		java.util.Map<java.util.Locale, String> nameMap,
		java.math.BigDecimal rate, String formatPattern, int maxFractionDigits,
		int minFractionDigits, String roundingMode, boolean primary,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"updateCommerceCurrency",
					_updateCommerceCurrencyParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCurrencyId, code, nameMap, rate, formatPattern,
					maxFractionDigits, minFractionDigits, roundingMode,
					primary, priority, active, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.currency.model.CommerceCurrency)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateExchangeRate(HttpPrincipal httpPrincipal,
		long commerceCurrencyId, String exchangeRateProviderKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"updateExchangeRate", _updateExchangeRateParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCurrencyId, exchangeRateProviderKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateExchangeRates(HttpPrincipal httpPrincipal,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCurrencyServiceUtil.class,
					"updateExchangeRates", _updateExchangeRatesParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceCurrencyServiceHttp.class);
	private static final Class<?>[] _addCommerceCurrencyParameterTypes0 = new Class[] {
			String.class, java.util.Map.class, java.math.BigDecimal.class,
			String.class, int.class, int.class, String.class, boolean.class,
			double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceCurrencyParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchPrimaryCommerceCurrencyParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceCurrenciesParameterTypes3 = new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceCurrenciesParameterTypes4 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceCurrenciesCountParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceCurrenciesCountParameterTypes6 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _getCommerceCurrencyParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceCurrencyParameterTypes8 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _setActiveParameterTypes9 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _setPrimaryParameterTypes10 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateCommerceCurrencyParameterTypes11 = new Class[] {
			long.class, String.class, java.util.Map.class,
			java.math.BigDecimal.class, String.class, int.class, int.class,
			String.class, boolean.class, double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateExchangeRateParameterTypes12 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _updateExchangeRatesParameterTypes13 = new Class[] {
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}