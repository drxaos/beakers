-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: liquibase/changelog.xml
-- Ran at: 07.03.15 2:02
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1425675719921
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'home-fca85f8b86 (192.168.0.100)', LOCKGRANTED = '2015-03-07 02:02:16.265' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset liquibase/updates/r0.01.01.xml::r0.01.01-1::xaos
CREATE TABLE guest_book (id BIGINT(19) AUTO_INCREMENT NOT NULL, version BIGINT(19) NOT NULL, guest_name VARCHAR(50) NOT NULL, message VARCHAR(1024) NOT NULL, CONSTRAINT CONSTRAINT_B PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.01-1', 'xaos', 'liquibase/updates/r0.01.01.xml', NOW(), 15, '7:307149d604653d33487ac15012ab4caa', 'createTable', '', 'EXECUTED', '3.3.2');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

