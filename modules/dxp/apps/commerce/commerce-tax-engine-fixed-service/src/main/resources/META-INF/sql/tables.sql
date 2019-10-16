create table CommerceTaxFixedRate (
	commerceTaxFixedRateId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPTaxCategoryId LONG,
	commerceTaxMethodId LONG,
	rate DOUBLE
);

create table CommerceTaxFixedRateAddressRel (
	CTaxFixedRateAddressRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceTaxMethodId LONG,
	CPTaxCategoryId LONG,
	commerceCountryId LONG,
	commerceRegionId LONG,
	zip VARCHAR(75) null,
	rate DOUBLE
);