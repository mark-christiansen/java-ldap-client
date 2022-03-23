#!/bin/bash
rm -r java-ldap-client
rm java-ldap-client.zip
cp target/java-ldap-client-0.0.1.jar lib
cp /Users/markchristiansen/.m2/repository/ch/qos/logback/logback-classic/1.2.11/logback-classic-1.2.11.jar lib
cp /Users/markchristiansen/.m2/repository/ch/qos/logback/logback-core/1.2.11/logback-core-1.2.11.jar lib
cp /Users/markchristiansen/.m2/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar lib
mkdir java-ldap-client
cp -r lib java-ldap-client
cp -r conf java-ldap-client
cp java-ldap-client.sh java-ldap-client
cp java-ldap-client.bat java-ldap-client
zip -r java-ldap-client.zip java-ldap-client
rm -r java-ldap-client