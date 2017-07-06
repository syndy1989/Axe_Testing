FROM jdk:windowsservercore

RUN mkdir C:\\axe

WORKDIR C:\\axe

COPY ./lib C:\\axe\\lib

COPY Script.java C:\\axe\\Script.java

COPY Parsing.java C:\\axe\\Parsing.java

COPY Testing.java C:\\axe\\Testing.java

COPY axe.min.js C:\\axe\\axe.min.js

COPY phantomjs.exe C:\\axe\\phantomjs.exe

COPY datafile.properties C:\\axe\\datafile.properties

RUN javac -cp "./lib/*" Script.java

ENTRYPOINT ["java"]

CMD ["-cp","./lib/*;" ,"Script"]

