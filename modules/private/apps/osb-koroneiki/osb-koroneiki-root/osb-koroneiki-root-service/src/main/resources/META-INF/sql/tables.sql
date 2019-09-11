create table Koroneiki_AuditEntry (
	auditEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	auditEntryKey VARCHAR(75) null,
	classNameId LONG,
	classPK LONG,
	auditSetId LONG,
	fieldClassNameId LONG,
	fieldClassPK LONG,
	action VARCHAR(75) null,
	field VARCHAR(75) null,
	oldLabel VARCHAR(255) null,
	oldValue STRING null,
	newLabel VARCHAR(255) null,
	newValue STRING null,
	description STRING null
);

create table Koroneiki_ExternalLink (
	externalLinkId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	externalLinkKey VARCHAR(75) null,
	classNameId LONG,
	classPK LONG,
	domain VARCHAR(75) null,
	entityName VARCHAR(255) null,
	entityId VARCHAR(150) null
);