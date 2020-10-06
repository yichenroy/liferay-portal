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

package com.liferay.commerce.discount.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.exception.CommerceDiscountLimitationTimesException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.service.CommerceDiscountUsageEntryLocalService;
import com.liferay.commerce.discount.test.util.CommerceDiscountTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.test.util.CommercePriceEntryTestUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.commerce.test.util.TestCommerceContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Alberti
 */
@RunWith(Arquillian.class)
public class CommerceDiscountUsageTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		PrincipalThreadLocal.setName(_user.getUserId());

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(), 0);

		_commerceAccount =
			_commerceAccountLocalService.getPersonalCommerceAccount(
				_user.getUserId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_commerceOrders = new ArrayList<>();
	}

	@After
	public void tearDown() throws Exception {
		for (CommerceOrder commerceOrder : _commerceOrders) {
			_commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
		}
	}

	@Test(expected = CommerceDiscountLimitationTimesException.class)
	public void testAccountLimitedCouponCodeDiscount() throws Exception {
		frutillaRule.scenario(
			"Discounts can be applied by coupon code"
		).given(
			"A product with a base price"
		).and(
			"A discount with account limitation times set to 3"
		).when(
			"I insert the correct coupon code in my context the discount is " +
				"correctly applied"
		).then(
			"When the same account uses the same coupon the 4th time I " +
				"receive an exception"
		);

		_assertDiscountLimitation(
			0, 3, 3,
			CommerceDiscountConstants.LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS);
	}

	@Test
	public void testApplyLimitedCouponCodeDiscountToProducts()
		throws Exception {

		frutillaRule.scenario(
			"Discounts can be applied by coupon code"
		).given(
			"A product with a base price"
		).and(
			"A discount with total limitation time set to 3"
		).when(
			"I insert the correct coupon code in my context the discount is " +
				"correctly applied"
		).then(
			"The discount usage should not be updated when getting the price " +
				"of a product"
		);

		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				RandomTestUtil.randomString(), _commerceCurrency.getCode(),
				LocaleUtil.US.getDisplayLanguage(), null,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				catalog.getGroupId());

		BigDecimal priceEntryPrice = BigDecimal.valueOf(35);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), StringPool.BLANK,
			priceEntryPrice);

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, commerceChannel, _user, _group, _commerceAccount,
			null);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance.getCPInstanceId(), 1, commerceContext);

		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		BigDecimal finalPrice = finalPriceMoney.getPrice();

		Assert.assertEquals(
			priceEntryPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), commerceChannel.getGroupId(), _commerceCurrency);

		commerceOrder.setCommerceCurrencyId(
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrderLocalService.updateCommerceOrder(commerceOrder);

		String couponCode = StringUtil.randomString();

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addCouponDiscount(
				_group.getGroupId(), 10, couponCode,
				CommerceDiscountConstants.LIMITATION_TYPE_LIMITED, 3, 0,
				CommerceDiscountConstants.TARGET_PRODUCTS,
				cpDefinition.getCPDefinitionId());

		commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance.getCPInstanceId(), 1, commerceContext);

		finalPriceMoney = commerceProductPrice.getFinalPrice();

		finalPrice = finalPriceMoney.getPrice();

		Assert.assertEquals(
			priceEntryPrice.stripTrailingZeros(),
			finalPrice.stripTrailingZeros());

		int commerceDiscountUsageCount =
			_commerceDiscountUsageEntryLocalService.
				getCommerceDiscountUsageEntriesCount(
					commerceDiscount.getCommerceDiscountId());

		Assert.assertEquals(0, commerceDiscountUsageCount);

		commerceOrder = _commerceOrderLocalService.applyCouponCode(
			commerceOrder.getCommerceOrderId(), couponCode, commerceContext);

		_commerceOrders.add(commerceOrder);

		commerceContext = new TestCommerceContext(
			_commerceCurrency, commerceChannel, _user, _group, _commerceAccount,
			commerceOrder);

		_commerceProductPriceCalculation.getCommerceProductPrice(
			cpInstance.getCPInstanceId(), 1, commerceContext);

		_commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder, _user.getUserId());

		commerceDiscountUsageCount =
			_commerceDiscountUsageEntryLocalService.
				getCommerceDiscountUsageEntriesCount(
					commerceDiscount.getCommerceDiscountId());

		Assert.assertEquals(1, commerceDiscountUsageCount);

		_commerceProductPriceCalculation.getCommerceProductPrice(
			cpInstance.getCPInstanceId(), 1, commerceContext);

		commerceDiscountUsageCount =
			_commerceDiscountUsageEntryLocalService.
				getCommerceDiscountUsageEntriesCount(
					commerceDiscount.getCommerceDiscountId());

		Assert.assertEquals(1, commerceDiscountUsageCount);
	}

	@Test
	public void testLimitedCouponCodeDiscountApplyAndRemove() throws Exception {
		frutillaRule.scenario(
			"Discounts can be applied by coupon code"
		).given(
			"A product with a base price"
		).and(
			"A discount with account limitation times set to 3"
		).when(
			"I insert the correct coupon code in my context the discount is " +
				"correctly applied"
		).then(
			"If i remove the coupon code from the order before checkout the " +
				"usage count is not changed"
		);

		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), commerceChannel.getGroupId(), _commerceCurrency);

		commerceOrder.setCommerceCurrencyId(
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrderLocalService.updateCommerceOrder(commerceOrder);

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				RandomTestUtil.randomString(), _commerceCurrency.getCode(),
				LocaleUtil.US.getDisplayLanguage(), null,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				catalog.getGroupId());

		BigDecimal priceEntryPrice = BigDecimal.valueOf(35);

		CommercePriceEntryTestUtil.addCommercePriceEntry(
			cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), StringPool.BLANK,
			priceEntryPrice);

		String couponCode = StringUtil.randomString();

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addCouponDiscount(
				_group.getGroupId(), 10, couponCode,
				CommerceDiscountConstants.TARGET_PRODUCTS,
				cpDefinition.getCPDefinitionId());

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, commerceChannel, _user, _group, _commerceAccount,
			commerceOrder);

		commerceOrder = _commerceOrderLocalService.applyCouponCode(
			commerceOrder.getCommerceOrderId(), couponCode, commerceContext);

		_commerceOrders.add(commerceOrder);

		int commerceDiscountUsageCount =
			_commerceDiscountUsageEntryLocalService.
				getCommerceDiscountUsageEntriesCount(
					commerceDiscount.getCommerceDiscountId());

		Assert.assertEquals(0, commerceDiscountUsageCount);

		_commerceOrderLocalService.applyCouponCode(
			commerceOrder.getCommerceOrderId(), null, commerceContext);

		commerceDiscountUsageCount =
			_commerceDiscountUsageEntryLocalService.
				getCommerceDiscountUsageEntriesCount(
					commerceDiscount.getCommerceDiscountId());

		Assert.assertEquals(0, commerceDiscountUsageCount);
	}

	@Test(expected = CommerceDiscountLimitationTimesException.class)
	public void testTotalAndAccountLimitedCouponCodeDiscount1()
		throws Exception {

		frutillaRule.scenario(
			"Discounts can be applied by coupon code"
		).given(
			"A product with a base price"
		).and(
			"A discount with total limitation time set to 5 and account " +
				"limitation times set to 3"
		).when(
			"I insert the correct coupon code in my context the discount is " +
				"correctly applied"
		).then(
			"When the same account uses the same coupon the 4th time I " +
				"receive an exception"
		);

		_assertDiscountLimitation(
			5, 3, 3,
			CommerceDiscountConstants.
				LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS_AND_TOTAL);
	}

	@Test(expected = CommerceDiscountLimitationTimesException.class)
	public void testTotalAndAccountLimitedCouponCodeDiscount2()
		throws Exception {

		frutillaRule.scenario(
			"Discounts can be applied by coupon code"
		).given(
			"A product with a base price"
		).and(
			"A discount with total limitation time set to 3 and account " +
				"limitation times set to 5"
		).when(
			"I insert the correct coupon code in my context the discount is " +
				"correctly applied"
		).then(
			"When an account uses the same coupon the 4th time I receive an " +
				"exception"
		);

		_assertDiscountLimitation(
			3, 5, 3,
			CommerceDiscountConstants.
				LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS_AND_TOTAL);
	}

	@Test(expected = CommerceDiscountLimitationTimesException.class)
	public void testTotalAndAccountLimitedCouponCodeDiscountGuestAccount()
		throws Exception {

		frutillaRule.scenario(
			"Discounts can be applied by coupon code"
		).given(
			"A product with a base price"
		).and(
			"A discount with total limitation time set to 3 and account " +
				"limitation times set to 5"
		).when(
			"I insert the correct coupon code in my context the discount is " +
				"correctly applied"
		).then(
			"When an account uses the same coupon the 4th time I receive an " +
				"exception"
		);

		_commerceAccount = _commerceAccountLocalService.getGuestCommerceAccount(
			_group.getCompanyId());

		_assertDiscountLimitation(
			3, 5, 3,
			CommerceDiscountConstants.
				LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS_AND_TOTAL);
	}

	@Test(expected = CommerceDiscountLimitationTimesException.class)
	public void testTotalLimitedCouponCodeDiscount() throws Exception {
		frutillaRule.scenario(
			"Discounts can be applied by coupon code"
		).given(
			"A product with a base price"
		).and(
			"A discount with total limitation times set to 3"
		).when(
			"I insert the correct coupon code in my context the discount is " +
				"correctly applied"
		).then(
			"When i try to use the same coupon the 4th time I receive an " +
				"exception"
		);

		_assertDiscountLimitation(
			3, 0, 3, CommerceDiscountConstants.LIMITATION_TYPE_LIMITED);
	}

	@Test
	public void testUnlimitedCouponCodeDiscount() throws Exception {
		frutillaRule.scenario(
			"Discounts can be applied by coupon code"
		).given(
			"A product with a base price"
		).and(
			"A discount with no limitation"
		).when(
			"I insert the correct coupon code in my context the discount is " +
				"correctly applied"
		).then(
			"The price of the product is correctly calculated"
		);

		_assertDiscountLimitation(
			0, 0, 3, CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED);
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private void _assertDiscountLimitation(
			int limitationTimes, int limitationTimesPerAccount,
			int numberOfOrders, String limitationType)
		throws Exception {

		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), _commerceCurrency.getCode());

		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				RandomTestUtil.randomString(), _commerceCurrency.getCode(),
				LocaleUtil.US.getDisplayLanguage(), null,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
				catalog.getGroupId());

		BigDecimal priceEntryPrice = BigDecimal.valueOf(35);

		CommercePriceEntry commercePriceEntry =
			CommercePriceEntryTestUtil.addCommercePriceEntry(
				cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), StringPool.BLANK,
				priceEntryPrice);

		String couponCode = StringUtil.randomString();

		CommerceDiscount commerceDiscount =
			CommerceDiscountTestUtil.addCouponDiscount(
				_group.getGroupId(), 10, couponCode, limitationType,
				limitationTimes, limitationTimesPerAccount,
				CommerceDiscountConstants.TARGET_PRODUCTS,
				cpDefinition.getCPDefinitionId());

		for (int i = 0; i < numberOfOrders; i++) {
			CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
				_user.getUserId(), commerceChannel.getGroupId(),
				_commerceCurrency);

			commerceOrder.setCommerceCurrencyId(
				_commerceCurrency.getCommerceCurrencyId());

			_commerceOrderLocalService.updateCommerceOrder(commerceOrder);

			CommerceContext commerceContext = new TestCommerceContext(
				_commerceCurrency, commerceChannel, _user, _group,
				_commerceAccount, commerceOrder);

			commerceOrder = _commerceOrderLocalService.applyCouponCode(
				commerceOrder.getCommerceOrderId(), couponCode,
				commerceContext);

			_commerceOrders.add(commerceOrder);

			BigDecimal actualPrice = BigDecimal.ZERO;
			BigDecimal discountPrice = BigDecimal.ZERO;

			commerceContext = new TestCommerceContext(
				_commerceCurrency, commerceChannel, _user, _group,
				_commerceAccount, commerceOrder);

			CommerceProductPrice commerceProductPrice =
				_commerceProductPriceCalculation.getCommerceProductPrice(
					cpInstance.getCPInstanceId(), 1, commerceContext);

			if (commerceProductPrice != null) {
				CommerceMoney finalPrice = commerceProductPrice.getFinalPrice();

				actualPrice = finalPrice.getPrice();

				CommerceDiscountValue discountValue =
					commerceProductPrice.getDiscountValue();

				CommerceMoney discountAmount =
					discountValue.getDiscountAmount();

				discountPrice = discountAmount.getPrice();
			}

			BigDecimal expectedPrice = commerceDiscount.getLevel1();

			Assert.assertEquals(
				expectedPrice.stripTrailingZeros(),
				discountPrice.stripTrailingZeros());

			BigDecimal price = commercePriceEntry.getPrice();

			expectedPrice = price.subtract(commerceDiscount.getLevel1());

			Assert.assertEquals(
				expectedPrice.stripTrailingZeros(),
				actualPrice.stripTrailingZeros());

			_commerceOrderEngine.checkoutCommerceOrder(
				commerceOrder, _user.getUserId());
		}

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), commerceChannel.getGroupId(), _commerceCurrency);

		commerceOrder.setCommerceCurrencyId(
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrderLocalService.updateCommerceOrder(commerceOrder);

		CommerceContext commerceContext = new TestCommerceContext(
			_commerceCurrency, commerceChannel, _user, _group, _commerceAccount,
			commerceOrder);

		_commerceOrders.add(commerceOrder);

		commerceOrder = _commerceOrderLocalService.applyCouponCode(
			commerceOrder.getCommerceOrderId(), couponCode, commerceContext);

		BigDecimal actualPrice = BigDecimal.ZERO;
		BigDecimal discountPrice = BigDecimal.ZERO;

		commerceContext = new TestCommerceContext(
			_commerceCurrency, commerceChannel, _user, _group, _commerceAccount,
			commerceOrder);

		CommerceProductPrice commerceProductPrice =
			_commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstance.getCPInstanceId(), 1, commerceContext);

		if (commerceProductPrice != null) {
			CommerceMoney finalPrice = commerceProductPrice.getFinalPrice();

			actualPrice = finalPrice.getPrice();

			CommerceDiscountValue discountValue =
				commerceProductPrice.getDiscountValue();

			CommerceMoney discountAmount = discountValue.getDiscountAmount();

			discountPrice = discountAmount.getPrice();
		}

		BigDecimal expectedPrice = commerceDiscount.getLevel1();

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			discountPrice.stripTrailingZeros());

		BigDecimal price = commercePriceEntry.getPrice();

		expectedPrice = price.subtract(commerceDiscount.getLevel1());

		Assert.assertEquals(
			expectedPrice.stripTrailingZeros(),
			actualPrice.stripTrailingZeros());

		_commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder, _user.getUserId());
	}

	private CommerceAccount _commerceAccount;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@Inject
	private CommerceDiscountUsageEntryLocalService
		_commerceDiscountUsageEntryLocalService;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	private List<CommerceOrder> _commerceOrders;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Inject
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@DeleteAfterTestRun
	private Company _company;

	private Group _group;
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}