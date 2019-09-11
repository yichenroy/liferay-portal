create index IX_E0843AF5 on Koroneiki_AuthenticationToken (digest[$COLUMN_LENGTH:75$], status);
create index IX_B0FB62DB on Koroneiki_AuthenticationToken (serviceProducerId);

create index IX_4D044B52 on Koroneiki_ServiceProducer (authorizationUserId);
create index IX_7A0F3811 on Koroneiki_ServiceProducer (uuid_[$COLUMN_LENGTH:75$], companyId);