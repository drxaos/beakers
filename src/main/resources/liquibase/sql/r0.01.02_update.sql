-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: liquibase/changelog.xml
-- Ran at: 3/11/15 2:22 PM
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1426065752566
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'xaos (192.168.111.18)', LOCKGRANTED = '2015-03-11 14:22:40.963' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset liquibase/updates/r0.01.02.xml::r0.01.02-1::xaos
CREATE TABLE session (id VARCHAR(255) NOT NULL, version BIGINT(19) NOT NULL, creation_time BIGINT(19) NOT NULL, data BLOB, last_accessed_time BIGINT(19) NOT NULL, max_inactive_interval_in_seconds INT(10) NOT NULL);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.02-1', 'xaos', 'liquibase/updates/r0.01.02.xml', NOW(), 16, '7:80049f503883a202901ba28f7763274d', 'createTable', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.02.xml::r0.01.02-2::xaos
ALTER TABLE session ADD CONSTRAINT CONSTRAINT_A PRIMARY KEY (id);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.02-2', 'xaos', 'liquibase/updates/r0.01.02.xml', NOW(), 17, '7:e9ca38e74c2960fa98236f2d15f4da93', 'addPrimaryKey', '', 'EXECUTED', '3.3.2');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

