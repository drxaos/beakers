-- *********************************************************************
-- SQL to roll back currently unexecuted changes
-- *********************************************************************
-- Change Log: liquibase/changelog.xml
-- Ran at: 3/11/15 2:22 PM
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1426065752566
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'xaos (192.168.111.18)', LOCKGRANTED = '2015-03-11 14:22:42.647' WHERE ID = 1 AND LOCKED = FALSE;

-- Rolling Back ChangeSet: liquibase/updates/r0.01.02.xml::r0.01.02-2::xaos
ALTER TABLE session DROP PRIMARY KEY;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.02-2' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.02.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.02.xml::r0.01.02-1::xaos
DROP TABLE session;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.02-1' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.02.xml';

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

