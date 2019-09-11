create table Koroneiki_AuthenticationToken (
	authenticationTokenId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	serviceProducerId LONG,
	name VARCHAR(75) null,
	prefix VARCHAR(75) null,
	digest VARCHAR(75) null,
	status INTEGER
);

create table Koroneiki_ServiceProducer (
	uuid_ VARCHAR(75) null,
	serviceProducerId LONG not null primary key,
	companyId LONG,
	userId LONG,
	authorizationUserId LONG,
	name VARCHAR(75) null,
	description STRING null
);