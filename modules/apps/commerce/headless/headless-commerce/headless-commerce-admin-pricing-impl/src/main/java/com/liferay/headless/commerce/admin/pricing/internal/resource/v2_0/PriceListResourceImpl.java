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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.commerce.price.list.service.CommercePriceListChannelRelService;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.commerce.price.list.service.CommercePriceListDiscountRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelService;
import com.liferay.commerce.pricing.service.CommercePriceModifierService;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListChannel;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListDiscount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifier;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.TierPrice;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.PriceListDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.odata.entity.v2_0.PriceListEntityModel;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceListAccountGroupUtil;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceListAccountUtil;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceListChannelUtil;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceListDiscountUtil;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceModifierUtil;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.TierPriceUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListResource;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.ExpandoUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/price-list.properties",
	scope = ServiceScope.PROTOTYPE, service = PriceListResource.class
)
public class PriceListResourceImpl extends BasePriceListResourceImpl {

	@Override
	public void deletePriceList(Long id) throws Exception {
		_commercePriceListService.deleteCommercePriceList(id);
	}

	@Override
	public void deletePriceListByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with externalReferenceCode: " +
					externalReferenceCode);
		}

		_commercePriceListService.deleteCommercePriceList(
			commercePriceList.getCommercePriceListId());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public PriceList getPriceList(Long id) throws Exception {
		return _toPriceList(GetterUtil.getLong(id));
	}

	@Override
	public PriceList getPriceListByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _toPriceList(commercePriceList.getCommercePriceListId());
	}

	@Override
	public Page<PriceList> getPriceListsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CommercePriceList.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setAttribute(
						"status", WorkflowConstants.STATUS_ANY);
					searchContext.setCompanyId(contextCompany.getCompanyId());
				}

			},
			sorts,
			document -> _toPriceList(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public PriceList patchPriceList(Long id, PriceList priceList)
		throws Exception {

		return _toPriceList(
			_updatePriceList(
				_commercePriceListService.getCommercePriceList(id), priceList));
	}

	@Override
	public PriceList patchPriceListByExternalReferenceCode(
			String externalReferenceCode, PriceList priceList)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _toPriceList(_updatePriceList(commercePriceList, priceList));
	}

	@Override
	public PriceList postPriceList(PriceList priceList) throws Exception {
		CommercePriceList commercePriceList = _upsertPriceList(priceList);

		return _toPriceList(commercePriceList.getCommercePriceListId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommercePriceList commercePriceList)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"DELETE", commercePriceList.getCommercePriceListId(),
				"deletePriceList", commercePriceList.getUserId(),
				"com.liferay.commerce.price.list.model.CommercePriceList",
				commercePriceList.getGroupId())
		).put(
			"get",
			addAction(
				"VIEW", commercePriceList.getCommercePriceListId(),
				"getPriceList", commercePriceList.getUserId(),
				"com.liferay.commerce.price.list.model.CommercePriceList",
				commercePriceList.getGroupId())
		).put(
			"permissions",
			addAction(
				"PERMISSIONS", commercePriceList.getCommercePriceListId(),
				"patchPriceList", commercePriceList.getUserId(),
				"com.liferay.commerce.price.list.model.CommercePriceList",
				commercePriceList.getGroupId())
		).put(
			"update",
			addAction(
				"UPDATE", commercePriceList.getCommercePriceListId(),
				"patchPriceList", commercePriceList.getUserId(),
				"com.liferay.commerce.price.list.model.CommercePriceList",
				commercePriceList.getGroupId())
		).build();
	}

	private DateConfig _getDisplayDateConfig(Date date, TimeZone timeZone) {
		if (date == null) {
			return new DateConfig(CalendarFactoryUtil.getCalendar(timeZone));
		}

		long time = date.getTime();

		Calendar calendar = CalendarFactoryUtil.getCalendar(time, timeZone);

		return new DateConfig(calendar);
	}

	private DateConfig _getExpirationDateConfig(Date date, TimeZone timeZone) {
		if (date == null) {
			Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
				timeZone);

			expirationCalendar.add(Calendar.MONTH, 1);

			return new DateConfig(expirationCalendar);
		}

		long time = date.getTime();

		Calendar calendar = CalendarFactoryUtil.getCalendar(time, timeZone);

		return new DateConfig(calendar);
	}

	private PriceList _toPriceList(CommercePriceList commercePriceList)
		throws Exception {

		return _toPriceList(commercePriceList.getCommercePriceListId());
	}

	private PriceList _toPriceList(Long commercePriceListId) throws Exception {
		CommercePriceList commercePriceList =
			_commercePriceListService.getCommercePriceList(commercePriceListId);

		return _priceListDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commercePriceList), _dtoConverterRegistry,
				commercePriceListId, contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser));
	}

	private CommercePriceList _updateNestedResources(
			PriceList priceList, CommercePriceList commercePriceList,
			ServiceContext serviceContext)
		throws Exception {

		// Price list account groups

		PriceListAccountGroup[] priceListAccountGroups =
			priceList.getPriceListAccountGroups();

		if (priceListAccountGroups != null) {
			for (PriceListAccountGroup priceListAccountGroup :
					priceListAccountGroups) {

				CommercePriceListCommerceAccountGroupRel
					commercePriceListCommerceAccountGroupRel =
						_commercePriceListCommerceAccountGroupRelService.
							fetchCommercePriceListCommerceAccountGroupRel(
								commercePriceList.getCommercePriceListId(),
								priceListAccountGroup.getAccountGroupId());

				if (commercePriceListCommerceAccountGroupRel != null) {
					continue;
				}

				PriceListAccountGroupUtil.
					addCommercePriceListCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commercePriceListCommerceAccountGroupRelService,
						priceListAccountGroup, commercePriceList,
						_serviceContextHelper);
			}
		}

		// Price list accounts

		PriceListAccount[] priceListAccounts = priceList.getPriceListAccounts();

		if (priceListAccounts != null) {
			for (PriceListAccount priceListAccount : priceListAccounts) {
				CommercePriceListAccountRel commercePriceListAccountRel =
					_commercePriceListAccountRelService.
						fetchCommercePriceListAccountRel(
							commercePriceList.getCommercePriceListId(),
							priceListAccount.getAccountId());

				if (commercePriceListAccountRel != null) {
					continue;
				}

				PriceListAccountUtil.addCommercePriceListAccountRel(
					_commerceAccountService,
					_commercePriceListAccountRelService, priceListAccount,
					commercePriceList, _serviceContextHelper);
			}
		}

		// Price list channels

		PriceListChannel[] priceListChannels = priceList.getPriceListChannels();

		if (priceListChannels != null) {
			for (PriceListChannel priceListChannel : priceListChannels) {
				CommercePriceListChannelRel commercePriceListChannelRel =
					_commercePriceListChannelRelService.
						fetchCommercePriceListChannelRel(
							commercePriceList.getCommercePriceListId(),
							priceListChannel.getPriceListId());

				if (commercePriceListChannelRel != null) {
					continue;
				}

				PriceListChannelUtil.addCommercePriceListChannelRel(
					_commerceChannelService,
					_commercePriceListChannelRelService, priceListChannel,
					commercePriceList, _serviceContextHelper);
			}
		}

		// Price list discounts

		PriceListDiscount[] priceListDiscounts =
			priceList.getPriceListDiscounts();

		if (priceListDiscounts != null) {
			for (PriceListDiscount priceListDiscount : priceListDiscounts) {
				CommercePriceListDiscountRel commercePriceListDiscountRel =
					_commercePriceListDiscountRelService.
						fetchCommercePriceListDiscountRel(
							commercePriceList.getCommercePriceListId(),
							priceListDiscount.getDiscountId());

				if (commercePriceListDiscountRel != null) {
					continue;
				}

				PriceListDiscountUtil.addCommercePriceListDiscountRel(
					_commerceDiscountService,
					_commercePriceListDiscountRelService, priceListDiscount,
					commercePriceList, _serviceContextHelper);
			}
		}

		// Price modifiers

		PriceModifier[] priceModifiers = priceList.getPriceModifiers();

		if (priceModifiers != null) {
			for (PriceModifier priceModifier : priceModifiers) {
				DateConfig displayDateConfig = _getDisplayDateConfig(
					priceModifier.getDisplayDate(),
					serviceContext.getTimeZone());

				DateConfig expirationDateConfig = _getExpirationDateConfig(
					priceModifier.getExpirationDate(),
					serviceContext.getTimeZone());

				CommercePriceModifier commercePriceModifier =
					_commercePriceModifierService.upsertCommercePriceModifier(
						serviceContext.getUserId(),
						GetterUtil.getLong(priceModifier.getId()),
						commercePriceList.getGroupId(),
						priceModifier.getTitle(), priceModifier.getTarget(),
						commercePriceList.getCommercePriceListId(),
						priceModifier.getModifierType(),
						priceModifier.getModifierAmount(),
						GetterUtil.get(priceList.getPriority(), 0D),
						GetterUtil.getBoolean(priceModifier.getActive(), true),
						displayDateConfig.getMonth(),
						displayDateConfig.getDay(), displayDateConfig.getYear(),
						displayDateConfig.getHour(),
						displayDateConfig.getMinute(),
						expirationDateConfig.getMonth(),
						expirationDateConfig.getDay(),
						expirationDateConfig.getYear(),
						expirationDateConfig.getHour(),
						expirationDateConfig.getMinute(),
						priceModifier.getExternalReferenceCode(),
						GetterUtil.getBoolean(
							priceModifier.getNeverExpire(), true),
						serviceContext);

				PriceModifierUtil.upsertCommercePriceModifierRels(
					_assetCategoryLocalService, _commercePricingClassService,
					_cProductLocalService, _commercePriceModifierRelService,
					priceModifier, commercePriceModifier,
					_serviceContextHelper);
			}
		}

		// Price entries

		PriceEntry[] priceEntries = priceList.getPriceEntries();

		if (priceEntries != null) {
			for (PriceEntry priceEntry : priceEntries) {
				DateConfig displayDateConfig = _getDisplayDateConfig(
					priceEntry.getDisplayDate(), serviceContext.getTimeZone());

				DateConfig expirationDateConfig = _getExpirationDateConfig(
					priceEntry.getExpirationDate(),
					serviceContext.getTimeZone());

				CommercePriceEntry commercePriceEntry =
					_commercePriceEntryService.upsertCommercePriceEntry(
						GetterUtil.getLong(priceEntry.getPriceEntryId()),
						GetterUtil.getLong(priceEntry.getSkuId()), null,
						commercePriceList.getCommercePriceListId(),
						priceEntry.getExternalReferenceCode(),
						BigDecimal.valueOf(priceEntry.getPrice()),
						priceEntry.getDiscountDiscovery(),
						priceEntry.getDiscountLevel1(),
						priceEntry.getDiscountLevel2(),
						priceEntry.getDiscountLevel3(),
						priceEntry.getDiscountLevel4(),
						displayDateConfig.getMonth(),
						displayDateConfig.getDay(), displayDateConfig.getYear(),
						displayDateConfig.getHour(),
						displayDateConfig.getMinute(),
						expirationDateConfig.getMonth(),
						expirationDateConfig.getDay(),
						expirationDateConfig.getYear(),
						expirationDateConfig.getHour(),
						expirationDateConfig.getMinute(),
						GetterUtil.getBoolean(
							priceEntry.getNeverExpire(), true),
						priceEntry.getSkuExternalReferenceCode(),
						serviceContext);

				TierPrice[] tierPrices = priceEntry.getTierPrices();

				if (tierPrices != null) {
					for (TierPrice tierPrice : tierPrices) {
						TierPriceUtil.upsertCommerceTierPriceEntry(
							_commerceTierPriceEntryService, tierPrice,
							commercePriceEntry, _serviceContextHelper);
					}
				}
			}
		}

		return commercePriceList;
	}

	private CommercePriceList _updatePriceList(
			CommercePriceList commercePriceList, PriceList priceList)
		throws Exception {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.getCommerceCurrency(
				contextCompany.getCompanyId(), priceList.getCurrencyCode());

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commercePriceList.getGroupId());

		DateConfig displayDateConfig = _getDisplayDateConfig(
			priceList.getDisplayDate(), serviceContext.getTimeZone());

		DateConfig expirationDateConfig = _getExpirationDateConfig(
			priceList.getExpirationDate(), serviceContext.getTimeZone());

		commercePriceList = _commercePriceListService.updateCommercePriceList(
			commercePriceList.getCommercePriceListId(),
			commerceCurrency.getCommerceCurrencyId(),
			GetterUtil.get(
				priceList.getNetPrice(), commercePriceList.isNetPrice()),
			GetterUtil.get(
				priceList.getParentPriceListId(),
				commercePriceList.getParentCommercePriceListId()),
			GetterUtil.get(priceList.getName(), commercePriceList.getName()),
			GetterUtil.get(
				priceList.getPriority(), commercePriceList.getPriority()),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.getBoolean(priceList.getNeverExpire(), true),
			serviceContext);

		// Expando

		Map<String, ?> customFields = priceList.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				serviceContext.getCompanyId(), CommercePriceList.class,
				commercePriceList.getPrimaryKey(), customFields);
		}

		// Update nested resources

		return _updateNestedResources(
			priceList, commercePriceList, serviceContext);
	}

	private CommercePriceList _upsertPriceList(PriceList priceList)
		throws Exception {

		CommerceCatalog commerceCatalog =
			_commerceCatalogService.getCommerceCatalog(
				priceList.getCatalogId());

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.getCommerceCurrency(
				contextCompany.getCompanyId(), priceList.getCurrencyCode());

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		DateConfig displayDateConfig = _getDisplayDateConfig(
			priceList.getDisplayDate(), serviceContext.getTimeZone());

		DateConfig expirationDateConfig = _getExpirationDateConfig(
			priceList.getExpirationDate(), serviceContext.getTimeZone());

		CommercePriceList commercePriceList =
			_commercePriceListService.upsertCommercePriceList(
				commerceCatalog.getGroupId(), contextUser.getUserId(), 0L,
				commerceCurrency.getCommerceCurrencyId(),
				GetterUtil.get(priceList.getNetPrice(), true),
				GetterUtil.get(
					priceList.getTypeAsString(),
					CommercePriceListConstants.TYPE_PRICE_LIST),
				GetterUtil.get(priceList.getParentPriceListId(), 0L),
				GetterUtil.get(priceList.getCatalogBasePriceList(), false),
				priceList.getName(),
				GetterUtil.get(priceList.getPriority(), 0D),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
				expirationDateConfig.getDay(), expirationDateConfig.getYear(),
				expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(),
				priceList.getExternalReferenceCode(),
				GetterUtil.getBoolean(priceList.getNeverExpire(), true),
				serviceContext);

		// Expando

		Map<String, ?> customFields = priceList.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				serviceContext.getCompanyId(), CommercePriceList.class,
				commercePriceList.getPrimaryKey(), customFields);
		}

		// Update nested resources

		return _updateNestedResources(
			priceList, commercePriceList, serviceContext);
	}

	private static final EntityModel _entityModel = new PriceListEntityModel();

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceCatalogService _commerceCatalogService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CommercePriceListAccountRelService
		_commercePriceListAccountRelService;

	@Reference
	private CommercePriceListChannelRelService
		_commercePriceListChannelRelService;

	@Reference
	private CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

	@Reference
	private CommercePriceListDiscountRelService
		_commercePriceListDiscountRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private CommercePriceModifierRelService _commercePriceModifierRelService;

	@Reference
	private CommercePriceModifierService _commercePriceModifierService;

	@Reference
	private CommercePricingClassService _commercePricingClassService;

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private PriceListDTOConverter _priceListDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}