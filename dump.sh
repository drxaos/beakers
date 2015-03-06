#!/bin/bash

dbName="dbName"
prodHost="root@production.example.com"

echo "drop database $dbName; create database $dbName;" | mysql -u root -proot $dbName
ssh $prodHost "mysqldump -uroot -p $dbName" | mysql -u root -proot $dbName
echo "update DATABASECHANGELOG set MD5SUM = NULL;" | mysql -u root -proot $dbName
