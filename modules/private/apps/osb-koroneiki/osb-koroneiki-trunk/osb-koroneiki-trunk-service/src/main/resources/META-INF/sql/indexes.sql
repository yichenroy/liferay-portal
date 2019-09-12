create index IX_D1166696 on Koroneiki_ProductConsumption (accountId);
create unique index IX_25801541 on Koroneiki_ProductConsumption (productConsumptionKey[$COLUMN_LENGTH:75$]);
create index IX_E22074E on Koroneiki_ProductConsumption (userId, accountId, productEntryId);
create index IX_D719E376 on Koroneiki_ProductConsumption (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_32A4FED8 on Koroneiki_ProductEntry (name[$COLUMN_LENGTH:75$]);
create unique index IX_2785F413 on Koroneiki_ProductEntry (productEntryKey[$COLUMN_LENGTH:75$]);
create index IX_EBC2538D on Koroneiki_ProductEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_2BD84FF6 on Koroneiki_ProductField (classNameId, classPK);

create index IX_E3929352 on Koroneiki_ProductPurchase (accountId);
create unique index IX_A40465A9 on Koroneiki_ProductPurchase (productPurchaseKey[$COLUMN_LENGTH:75$]);
create index IX_3E312B3A on Koroneiki_ProductPurchase (uuid_[$COLUMN_LENGTH:75$], companyId);