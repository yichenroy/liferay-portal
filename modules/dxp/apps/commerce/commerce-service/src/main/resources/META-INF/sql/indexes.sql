create unique index IX_726123B7 on CPDefinitionAvailabilityRange (CPDefinitionId);
create index IX_9032BB82 on CPDefinitionAvailabilityRange (groupId);
create index IX_58536E1C on CPDefinitionAvailabilityRange (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_64D8CB9E on CPDefinitionAvailabilityRange (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_6DCF2DAB on CommerceAddress (addressUserId);
create index IX_CD76FE87 on CommerceAddress (commerceCountryId);
create index IX_71C5A9DD on CommerceAddress (commerceRegionId);
create index IX_B2D4128D on CommerceAddress (groupId, addressUserId, defaultBilling);
create index IX_F3EF45C0 on CommerceAddress (groupId, addressUserId, defaultShipping);

create index IX_DC0A8E5D on CommerceAvailabilityRange (groupId);
create index IX_A7FBA1A1 on CommerceAvailabilityRange (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_FD921C63 on CommerceAvailabilityRange (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_73579769 on CommerceCart (billingAddressId);
create index IX_13E7A9AC on CommerceCart (groupId, type_);
create index IX_280446D3 on CommerceCart (groupId, userId, name[$COLUMN_LENGTH:75$], type_);
create index IX_125C7830 on CommerceCart (shippingAddressId);
create index IX_BC3AFD75 on CommerceCart (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_73DE5D37 on CommerceCart (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_1F9EE843 on CommerceCartItem (CPDefinitionId);
create index IX_8F602B45 on CommerceCartItem (CPInstanceId);
create index IX_7B97EC1E on CommerceCartItem (commerceCartId, CPDefinitionId, CPInstanceId);

create index IX_7BB13C80 on CommerceCountry (groupId, active_);
create index IX_FEDECABF on CommerceCountry (groupId, billingAllowed, active_);
create index IX_158112E8 on CommerceCountry (groupId, shippingAllowed, active_);
create index IX_91EA24D5 on CommerceCountry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_7EFDC97 on CommerceCountry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_819B13B6 on CommerceInventory (CPDefinitionId);
create index IX_80D15A5B on CommerceInventory (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_7412BB9D on CommerceInventory (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_64F208B1 on CommerceOrder (groupId);

create index IX_FD99BA9B on CommerceOrderItem (CPDefinitionId);
create index IX_2E1BB39D on CommerceOrderItem (CPInstanceId);
create index IX_945497D2 on CommerceOrderItem (commerceOrderId);

create index IX_1FB6FD31 on CommercePaymentMethod (groupId, active_);
create unique index IX_B0FDFD55 on CommercePaymentMethod (groupId, engineKey[$COLUMN_LENGTH:75$]);

create index IX_F49296DB on CommercePriceEntry (CPInstanceId);
create index IX_CA7A2D0D on CommercePriceEntry (commercePriceListId);
create index IX_5E36B51E on CommercePriceEntry (companyId);
create index IX_E185EB20 on CommercePriceEntry (groupId);
create index IX_1578F03E on CommercePriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F45C6E40 on CommercePriceEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_473B4D8D on CommercePriceList (commerceCurrencyId);
create index IX_2AA1AF56 on CommercePriceList (companyId);
create index IX_31913054 on CommercePriceList (displayDate, status);
create index IX_2C5B7A3E on CommercePriceList (groupId, status);
create index IX_FCE28706 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_554D1708 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_49C93338 on CommerceRegion (commerceCountryId, active_);
create index IX_3BC85C89 on CommerceRegion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DBA0714B on CommerceRegion (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_42E5F6EF on CommerceShippingMethod (groupId, active_);
create unique index IX_C4557F93 on CommerceShippingMethod (groupId, engineKey[$COLUMN_LENGTH:75$]);

create index IX_7E5B23A5 on CommerceTirePriceEntry (commercePriceEntryId);
create index IX_E0351A16 on CommerceTirePriceEntry (companyId);
create index IX_C7492E18 on CommerceTirePriceEntry (groupId);
create index IX_91658446 on CommerceTirePriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_4CEE6448 on CommerceTirePriceEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_4500A0CA on CommerceWarehouse (groupId, commerceCountryId);

create index IX_3AF0DDD6 on CommerceWarehouseItem (classNameId, classPK);
create unique index IX_74C3AC07 on CommerceWarehouseItem (commerceWarehouseId, classNameId, classPK);