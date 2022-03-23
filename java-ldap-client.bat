set TRUSTSTORE="c:/myapp/mytruststore.jks"
set TRUSTSTORE_PW="password"
java -Djavax.net.ssl.trustStore=%TRUSTSTORE% -Djavax.net.ssl.keyStorePassword=%TRUSTSTORE_PWD% -cp "lib/*;conf" com.publix.ldap.LdapClient