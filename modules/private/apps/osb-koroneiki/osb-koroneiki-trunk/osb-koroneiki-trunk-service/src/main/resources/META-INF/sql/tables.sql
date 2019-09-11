create table Koroneiki_ProductConsumption (
	uuid_ VARCHAR(75) null,
	productConsumptionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	productConsumptionKey VARCHAR(75) null,
	accountId LONG,
	productEntryId LONG
);

create table Koroneiki_ProductEntry (
	uuid_ VARCHAR(75) null,
	productEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	productEntryKey VARCHAR(75) null,
	name VARCHAR(75) null
);

create table Koroneiki_ProductField (
	productFieldId LONG not null primary key,
	companyId LONG,
	userId LONG,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null,
	value VARCHAR(75) null
);

create table Koroneiki_ProductPurchase (
	uuid_ VARCHAR(75) null,
	productPurchaseId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	productPurchaseKey VARCHAR(75) null,
	accountId LONG,
	productEntryId LONG,
	startDate DATE null,
	endDate DATE null,
	quantity INTEGER
);