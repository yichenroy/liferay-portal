create table WorkflowMetricsSLACalendar (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	workflowMetricsSLACalendarId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null
);

create table WorkflowMetricsSLACondition (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	workflowMetricsSLAConditionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	workflowMetricsSLADefinitionId LONG
);

create table WorkflowMetricsSLADefinition (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	workflowMetricsSLADefinitionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description TEXT null,
	duration LONG,
	processId LONG,
	pauseNodeKeys VARCHAR(75) null,
	startNodeKeys VARCHAR(75) null,
	stopNodeKeys VARCHAR(75) null
);