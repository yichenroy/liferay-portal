create table Koroneiki_Account (
	uuid_ VARCHAR(75) null,
	accountId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	accountKey VARCHAR(75) null,
	parentAccountId LONG,
	name VARCHAR(150) null,
	code_ VARCHAR(75) null,
	description STRING null,
	notes STRING null,
	logoId LONG,
	contactEmailAddress VARCHAR(75) null,
	profileEmailAddress VARCHAR(75) null,
	phoneNumber VARCHAR(75) null,
	faxNumber VARCHAR(75) null,
	website VARCHAR(75) null,
	industry VARCHAR(75) null,
	tier VARCHAR(75) null,
	soldBy VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	statusMessage VARCHAR(75) null
);

create table Koroneiki_Contact (
	uuid_ VARCHAR(75) null,
	contactId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	contactKey VARCHAR(75) null,
	oktaId VARCHAR(75) null,
	firstName VARCHAR(75) null,
	middleName VARCHAR(75) null,
	lastName VARCHAR(75) null,
	emailAddress VARCHAR(75) null,
	languageId VARCHAR(75) null
);

create table Koroneiki_ContactAccountRole (
	contactId LONG not null,
	accountId LONG not null,
	contactRoleId LONG not null,
	primary key (contactId, accountId, contactRoleId)
);

create table Koroneiki_ContactRole (
	uuid_ VARCHAR(75) null,
	contactRoleId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	contactRoleKey VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	type_ INTEGER,
	system_ BOOLEAN
);

create table Koroneiki_ContactTeamRole (
	contactId LONG not null,
	teamId LONG not null,
	contactRoleId LONG not null,
	primary key (contactId, teamId, contactRoleId)
);

create table Koroneiki_Team (
	uuid_ VARCHAR(75) null,
	teamId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	teamKey VARCHAR(75) null,
	accountId LONG,
	name VARCHAR(75) null
);

create table Koroneiki_TeamAccountRole (
	teamId LONG not null,
	accountId LONG not null,
	teamRoleId LONG not null,
	primary key (teamId, accountId, teamRoleId)
);

create table Koroneiki_TeamRole (
	uuid_ VARCHAR(75) null,
	teamRoleId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	teamRoleKey VARCHAR(75) null,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	type_ INTEGER
);