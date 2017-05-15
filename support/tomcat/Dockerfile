FROM tomcat:8.0
MAINTAINER eshop

RUN rm -fr /usr/local/tomcat/webapps/*

#ADD server.xml /usr/local/tomcat/conf/server.xml
#ADD catalina.sh /usr/local/tomcat/bin/catalina.sh

#RUN chmod 777 /usr/local/tomcat/bin/catalina.sh

VOLUME /usr/local/tomcat/webapps

CMD ["catalina.sh", "run"]