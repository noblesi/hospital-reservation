@echo off
rem Copy this file to %CATALINA_HOME%\bin\setenv.bat on the Tomcat 9 server.
rem Replace the placeholder values before starting Tomcat.
rem Do not commit real production credentials.

set "JAVA_OPTS=%JAVA_OPTS% -Dhospital.db.url=jdbc:oracle:thin:@DB_HOST:1521:orcl"
set "JAVA_OPTS=%JAVA_OPTS% -Dhospital.db.username=DB_USER"
set "JAVA_OPTS=%JAVA_OPTS% -Dhospital.db.password=DB_PASSWORD"
set "JAVA_OPTS=%JAVA_OPTS% -Dhospital.key.file=C:/secure/qoeryqoeryqoe.txt"
set "JAVA_OPTS=%JAVA_OPTS% -Dhospital.doctor.image.dir=C:/hospital/uploads/doctors"
