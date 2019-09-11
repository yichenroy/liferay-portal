create unique index IX_F928EB2B on Koroneiki_AuditEntry (auditEntryKey[$COLUMN_LENGTH:75$]);
create index IX_F0E63C02 on Koroneiki_AuditEntry (classNameId, classPK);

create index IX_E6C22B10 on Koroneiki_ExternalLink (classNameId, classPK);
create index IX_CFD4A57F on Koroneiki_ExternalLink (classNameId, domain[$COLUMN_LENGTH:75$], entityName[$COLUMN_LENGTH:255$], entityId[$COLUMN_LENGTH:150$]);
create unique index IX_97BE6ECF on Koroneiki_ExternalLink (externalLinkKey[$COLUMN_LENGTH:75$]);