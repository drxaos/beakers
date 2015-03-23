-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: liquibase/changelog.xml
-- Ran at: 3/6/15 5:11 PM
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1425643908954
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'xaos (192.168.111.18)', LOCKGRANTED = '2015-03-06 17:11:54.137' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-1::xaos
CREATE TABLE role (id BIGINT(19) AUTO_INCREMENT NOT NULL, version BIGINT(19) NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT CONSTRAINT_2 PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-1', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 1, '7:baee8e5a9474194e024343604ed13a4d', 'createTable', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-2::xaos
CREATE TABLE role_group (id BIGINT(19) AUTO_INCREMENT NOT NULL, version BIGINT(19) NOT NULL, name VARCHAR(255) NOT NULL, CONSTRAINT CONSTRAINT_B PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-2', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 2, '7:ff56d0f5b6d38ad16f47d10ea5f40d68', 'createTable', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-3::xaos
CREATE TABLE role_group_roles (role_id BIGINT(19) NOT NULL, role_group_id BIGINT(19) NOT NULL);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-3', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 3, '7:08f1a7de4020fb0aac0ca873be15ea01', 'createTable', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-4::xaos
CREATE TABLE sys_mail_log (id BIGINT(19) AUTO_INCREMENT NOT NULL, version BIGINT(19) NOT NULL, date TIMESTAMP, sender VARCHAR(255), subject VARCHAR(255), text TEXT, to_email VARCHAR(255), view VARCHAR(255), CONSTRAINT CONSTRAINT_A PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-4', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 4, '7:5dc6dde9f5a3586c8fb19cef661b0ef5', 'createTable', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-5::xaos
CREATE TABLE system (id BIGINT(19) AUTO_INCREMENT NOT NULL, version BIGINT(19) NOT NULL, parameter VARCHAR(255) NOT NULL, value VARCHAR(255), CONSTRAINT CONSTRAINT_9 PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-5', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 5, '7:cef2c501619f09bd7342ef1b734e3855', 'createTable', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-6::xaos
CREATE TABLE user (id BIGINT(19) AUTO_INCREMENT NOT NULL, version BIGINT(19) NOT NULL, created TIMESTAMP NOT NULL, email VARCHAR(255) NOT NULL, full_name VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, role_group_id BIGINT(19) NOT NULL, username VARCHAR(255) NOT NULL, CONSTRAINT CONSTRAINT_27 PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-6', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 6, '7:0f972a24c375f7861b360ea3d7a04d93', 'createTable', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-7::xaos
ALTER TABLE role_group_roles ADD CONSTRAINT CONSTRAINT_26 PRIMARY KEY (role_group_id, role_id);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-7', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 7, '7:cd05ef6bec4dedb94815439c31b1cc52', 'addPrimaryKey', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-8::xaos
ALTER TABLE role_group ADD CONSTRAINT uk_35hmlucbb2ndj6w0qxrocer9k UNIQUE (name);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-8', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 8, '7:6c5ed9c2c362721df942654ee198b0d5', 'addUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-9::xaos
ALTER TABLE role ADD CONSTRAINT uk_8sewwnpamngi6b1dwaa88askk UNIQUE (name);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-9', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 9, '7:0fe2406e297423cb1fb38eafe778ae4a', 'addUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-10::xaos
ALTER TABLE user ADD CONSTRAINT uk_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-10', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 10, '7:a107f268c736f8b76d116875dabac13c', 'addUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-11::xaos
ALTER TABLE user ADD CONSTRAINT uk_sb8bbouer5wak8vyiiy4pf2bx UNIQUE (username);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-11', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 11, '7:2f6c28684210572cb53581df4c303b6f', 'addUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-12::xaos
ALTER TABLE role_group_roles ADD CONSTRAINT fk_epbndsvnpe8ebd7ebawd4apvs FOREIGN KEY (role_id) REFERENCES role (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-12', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 12, '7:a7bd500fdd38e739e0e8eacf289364b8', 'addForeignKeyConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-13::xaos
ALTER TABLE role_group_roles ADD CONSTRAINT fk_qriqmfaudiptqutqsuen0oalo FOREIGN KEY (role_group_id) REFERENCES role_group (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-13', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 13, '7:c6ecb2242772a2d9570b1eb68b862541', 'addForeignKeyConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.00.xml::r0.01.00-14::xaos
ALTER TABLE user ADD CONSTRAINT fk_rhtrpvmnw8qrhp1yflc4yaw2s FOREIGN KEY (role_group_id) REFERENCES role_group (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-14', 'xaos', 'liquibase/updates/r0.01.00.xml', NOW(), 14, '7:ade0ffccc9a50dab7fe4c0e5e6e2e612', 'addForeignKeyConstraint', '', 'EXECUTED', '3.3.2');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

