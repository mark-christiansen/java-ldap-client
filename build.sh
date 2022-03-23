#!/bin/bash
rm -r java-ldap-client
rm java-ldap-client.zip
rm lib/*
cp target/java-ldap-client-0.0.1.jar lib
cp /Users/markchristiansen/.m2/repository/ch/qos/logback/logback-classic/1.2.11/logback-classic-1.2.11.jar lib
cp /Users/markchristiansen/.m2/repository/ch/qos/logback/logback-core/1.2.11/logback-core-1.2.11.jar lib
cp /Users/markchristiansen/.m2/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar lib
#cp /Users/markchristiansen/.m2/repository/io/confluent/auth-providers/7.0.1-ce/auth-providers-7.0.1-ce.jar lib
#cp /Users/markchristiansen/.m2/repository/org/apache/kafka/kafka-clients/7.0.1-ce/kafka-clients-7.0.1-ce.jar lib
mkdir java-ldap-client
cp -r lib java-ldap-client
cp -r conf java-ldap-client
cp java-ldap-client.sh java-ldap-client
cp java-ldap-client.bat java-ldap-client
zip -r java-ldap-client.zip java-ldap-client
rm -r java-ldap-client