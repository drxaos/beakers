-- *********************************************************************
-- SQL to roll back currently unexecuted changes
-- *********************************************************************
-- Change Log: liquibase/changelog.xml
-- Ran at: 3/6/15 5:11 PM
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1425643908954
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'xaos (192.168.111.18)', LOCKGRANTED = '2015-03-06 17:11:54.650' WHERE ID = 1 AND LOCKED = FALSE;

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-14::xaos
ALTER TABLE user DROP CONSTRAINT fk_rhtrpvmnw8qrhp1yflc4yaw2s;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-14' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-13::xaos
ALTER TABLE role_group_roles DROP CONSTRAINT fk_qriqmfaudiptqutqsuen0oalo;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-13' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-12::xaos
ALTER TABLE role_group_roles DROP CONSTRAINT fk_epbndsvnpe8ebd7ebawd4apvs;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-12' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-11::xaos
ALTER TABLE user DROP CONSTRAINT uk_sb8bbouer5wak8vyiiy4pf2bx;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-11' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-10::xaos
ALTER TABLE user DROP CONSTRAINT uk_ob8kqyqqgmefl0aco34akdtpe;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-10' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-9::xaos
ALTER TABLE role DROP CONSTRAINT uk_8sewwnpamngi6b1dwaa88askk;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-9' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-8::xaos
ALTER TABLE role_group DROP CONSTRAINT uk_35hmlucbb2ndj6w0qxrocer9k;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-8' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-7::xaos
ALTER TABLE role_group_roles DROP PRIMARY KEY;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-7' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-6::xaos
DROP TABLE user;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-6' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-5::xaos
DROP TABLE system;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-5' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-4::xaos
DROP TABLE sys_mail_log;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-4' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-3::xaos
DROP TABLE role_group_roles;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-3' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-2::xaos
DROP TABLE role_group;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-2' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Rolling Back ChangeSet: liquibase/updates/r0.01.00.xml::r0.01.00-1::xaos
DROP TABLE role;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-1' AND AUTHOR='xaos' AND FILENAME='liquibase/updates/r0.01.00.xml';

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

