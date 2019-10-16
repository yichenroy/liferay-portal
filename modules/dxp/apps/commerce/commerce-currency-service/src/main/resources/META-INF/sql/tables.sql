create table CommerceCurrency (
	uuid_ VARCHAR(75) null,
	commerceCurrencyId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	code_ STRING null,
	name STRING null,
	rate DOUBLE,
	roundingType VARCHAR(75) null,
	primary_ BOOLEAN,
	priority DOUBLE,
	active_ BOOLEAN,
	lastPublishDate DATE null
);