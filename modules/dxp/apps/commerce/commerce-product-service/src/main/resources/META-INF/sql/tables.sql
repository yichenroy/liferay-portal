create table CommerceProductDefinition (
	uuid_ VARCHAR(75) null,
	commerceProductDefinitionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	productTypeName VARCHAR(75) null,
	availableIndividually BOOLEAN,
	DDMStructureKey VARCHAR(75) null,
	baseSKU VARCHAR(75) null,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	defaultLanguageId VARCHAR(75) null
);

create table CommerceProductDefinitionLocalization (
	mvccVersion LONG default 0 not null,
	commerceProductDefinitionLocalizationId LONG not null primary key,
	companyId LONG,
	commerceProductDefinitionPK LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null,
	urlTitle VARCHAR(75) null,
	description VARCHAR(75) null
);

create table CommerceProductDefinitionOptionRel (
	uuid_ VARCHAR(75) null,
	definitionOptionRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceProductDefinitionId LONG,
	commerceProductOptionId LONG,
	name STRING null,
	description STRING null,
	DDMFormFieldTypeName VARCHAR(75) null,
	priority INTEGER,
	facetable BOOLEAN,
	skuContributor BOOLEAN
);

create table CommerceProductDefinitionOptionValueRel (
	uuid_ VARCHAR(75) null,
	definitionOptionValueRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	definitionOptionRelId LONG,
	title STRING null,
	priority INTEGER
);

create table CommerceProductInstance (
	uuid_ VARCHAR(75) null,
	commerceProductInstanceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceProductDefinitionId LONG,
	sku VARCHAR(75) null,
	DDMContent TEXT null,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CommerceProductOption (
	uuid_ VARCHAR(75) null,
	commerceProductOptionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	DDMFormFieldTypeName VARCHAR(75) null
);

create table CommerceProductOptionValue (
	uuid_ VARCHAR(75) null,
	commerceProductOptionValueId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceProductOptionId LONG,
	title STRING null,
	priority INTEGER
);