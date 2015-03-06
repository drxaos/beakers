-- *********************************************************************
-- SQL to roll back currently unexecuted changes
-- *********************************************************************
-- Change Log: liquibase/changelog.xml
-- Ran at: 07.03.15 2:02
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1425675719921
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'home-fca85f8b86 (192.168.0.100)', LOCKGRANTED = '2015-03-07 02:02:17.609' WHERE ID = 1 AND LOCKED = FALSE;

-- Rolling Back ChangeSet: liquibase/updates/r0.01.01.xml::r0.01.01-1::xaos
DROP TABLE guest_book;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.01-1' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.01.xml';

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

