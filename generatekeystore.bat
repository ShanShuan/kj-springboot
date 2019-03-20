::  Generate server key and self signed server certificate
keytool -genkey -alias tomcat -keyalg RSA -storetype PKCS12 -keysize 2048 -keystore ./src/main/resources/serverkeystore.p12 -ext SAN=dns:abc.com,dns:localhost,ip:127.0.0.1,ip:192.168.1.108 -validity 3650
::  -ext SAN=dns:abc.com,dns:192.168.1.108,ip:127.0.0.1

::  Generate client key and self signed client certificate
::  keytool -genkey -alias hlthspvclient -keyalg RSA -storetype PKCS12 -keysize 2048 -keystore ./src/main/resources/clientkeystore.p12 -ext SAN=dns:def.com,dns:localhost,ip:192.168.1.108 -validity 3650

::  Export the server certificate
keytool -export -alias tomcat -file ./src/main/resources/servercert.cer -keystore ./src/main/resources/serverkeystore.p12

::  Generate openssl certificate execute on 10.40.0.197
openssl x509 -in servercert.cer -inform der -outform pem -out servercert.cer.pem


::  Export the client certificate
::  keytool -export -alias hlthspvclient -file ./src/main/resources/clientcert.cer -keystore ./src/main/resources/clientkeystore.p12

::  Import cert to $JAVA_HOME/jre/lib/security
:: sudo keytool -import -trustcacerts -alias localhost -file localhost.crt -keystore $JAVA_HOME/jre/lib/security/cacerts

:: 输入密钥库口令：hlthspvJurassic!@#$qwer
pause