FROM microsoft/windowsservercore

# $ProgressPreference: https://github.com/PowerShell/PowerShell/issues/2138#issuecomment-251261324
SHELL ["powershell", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

ENV JAVA_HOME C:\\ojdkbuild
RUN $newPath = ('{0}\bin;{1}' -f $env:JAVA_HOME, $env:PATH); \
	Write-Host ('Updating PATH: {0}' -f $newPath); \
# Nano Server does not have "[Environment]::SetEnvironmentVariable()"
	setx /M PATH $newPath;

# https://github.com/ojdkbuild/ojdkbuild/releases
ENV JAVA_VERSION 8u131
ENV JAVA_OJDKBUILD_VERSION 1.8.0.131-1
ENV JAVA_OJDKBUILD_ZIP java-1.8.0-openjdk-1.8.0.131-1.b11.ojdkbuild.windows.x86_64.zip
ENV JAVA_OJDKBUILD_SHA256 7e7384636054001499ba96d55c90fc39cbb0441281254a1e9ac8510b527a7a46

RUN $url = ('https://github.com/ojdkbuild/ojdkbuild/releases/download/{0}/{1}' -f $env:JAVA_OJDKBUILD_VERSION, $env:JAVA_OJDKBUILD_ZIP); \
	Write-Host ('Downloading {0} ...' -f $url); \
	Invoke-WebRequest -Uri $url -OutFile 'ojdkbuild.zip'; \
	Write-Host ('Verifying sha256 ({0}) ...' -f $env:JAVA_OJDKBUILD_SHA256); \
	if ((Get-FileHash ojdkbuild.zip -Algorithm sha256).Hash -ne $env:JAVA_OJDKBUILD_SHA256) { \
		Write-Host 'FAILED!'; \
		exit 1; \
	}; \
	\
	Write-Host 'Expanding ...'; \
	Expand-Archive ojdkbuild.zip -DestinationPath C:\; \
	\
	Write-Host 'Renaming ...'; \
	Move-Item \
		-Path ('C:\{0}' -f ($env:JAVA_OJDKBUILD_ZIP -Replace '.zip$', '')) \
		-Destination $env:JAVA_HOME \
	; \
	\
	Write-Host 'Verifying install ...'; \
	Write-Host '  java -version'; java -version; \
	Write-Host '  javac -version'; javac -version; \
	\
	Write-Host 'Removing ...'; \
	Remove-Item ojdkbuild.zip -Force; \
	\
	Write-Host 'Complete.';

RUN mkdir C:\\axe

WORKDIR C:\\axe

COPY ./lib C:\\axe\\lib

COPY Script.java C:\\axe\\Script.java

COPY axe.min.js C:\\axe\\axe.min.js

COPY phantomjs.exe C:\\axe\\phantomjs.exe

COPY datafile.properties C:\\axe\\datafile.properties

RUN javac -cp "./lib/*;" C:\\axe\\Script.java

ENTRYPOINT ["java"]

CMD ["-cp",""axe-selenium-2.0;com.nft.testing_axe;com.nft.testing_parser;java-json;json-simple-1.1;phantomjsdriver-1.2.1;selenium-driver-helper-phantomjs-2.1.1;selenium-java-2.45.0;selenium-server-standalone-3.4.0"" ,"Script"]

