@echo off
rem mode con cols=113 lines=9999
rem set CATALINA_HOME
set CUR_HOME=%~dp0
cd %CUR_HOME%/..
set OSGI_HOME=%cd%
set CATALINA_HOME=%OSGI_HOME%
set CATALINA_BASE=%OSGI_HOME%
set osgi.home=%OSGI_HOME%
echo Using CATALINA_BASE: %CATALINA_HOME%

set JAVA_OPTS=%JAVA_OPTS% -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%OSGI_HOME%/dump

rem clean %CATALINA_HOME%\webapps
rd %CATALINA_HOME%\webapps /s /q
mkdir %CATALINA_HOME%\webapps
rd %CATALINA_HOME%\work /s /q
mkdir %CATALINA_HOME%\work

set PATH=D:\Java\jdk1.7\bin;%PATH%

rem create new confi.ini
rem call %OSGI_HOME%/script/combinationAll.bat

rem set jvm info
set JVM_OSGI_OPTS=-Dosgi.war.delay=60000 -Djava.ext.dirs="%OSGI_HOME%;%OSGI_HOME%/lib;%OSGI_HOME%/lib/ext;%OSGI_HOME%/lib/conf;%OSGI_HOME%/lib/cus;%JAVA_HOME%/jre/lib/ext" -Dosgi.config.dir="%CATALINA_HOME%/pconfig" -Dosgi.configbak.dir="%CATALINA_HOME%/pconfig/bak" -Djava.io.tmpdir="%CATALINA_HOME%/webapps" -Dosgi.parentClassloader=ext -Dosgi.compatibility.bootdelegation=true -Dosgi.configuration.area=%OSGI_HOME%/config/configuration_ext

rem set CATALINA info
set CATALINA_OSGI_OPTS=-Dcatalina.home="%CATALINA_HOME%" -Dcatalina.base="%CATALINA_HOME%" -Dosgi.home="%CATALINA_HOME%"


if ""%1"" == """" goto doRun
if ""%1"" == ""run"" goto doRun


:doRun
echo start osgi in run env
java %JAVA_OPTS% %JVM_OSGI_OPTS% %CATALINA_OSGI_OPTS% -jar %OSGI_HOME%/org.eclipse.osgi.jar
